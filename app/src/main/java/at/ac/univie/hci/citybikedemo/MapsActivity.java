package at.ac.univie.hci.citybikedemo;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.ac.univie.hci.citybikedemo.model.Network;
import at.ac.univie.hci.citybikedemo.utility.HttpRequest;
import at.ac.univie.hci.citybikedemo.utility.MyApplication;
import at.ac.univie.hci.citybikedemo.utility.Parser;

public class MapsActivity extends Activity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private Button refreshButton;
    private Button changeCityButton;
    private Button listViewButton;

    private List<Network> networks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //builds a GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        networks = new ArrayList<>();
        //gets the information passed from the other activity
        Bundle bundle = getIntent().getExtras();
        final ArrayList<String> networksHrefs = bundle.getStringArrayList("hrefs");
        for (int i = 0; i < networksHrefs.size(); i++){
            try {
                //Executes a HttpRequest and waits for respond
                String responseBody = new HttpRequest(networksHrefs.get(i)).execute().get(5000, TimeUnit.MILLISECONDS);
                try {
                    //gets the JSON objects network
                    JSONObject jsonNetwork = new JSONObject(responseBody).getJSONObject("network");
                    //adds a java version of the Network to the networks list
                    networks.add(Parser.parseNetwork(jsonNetwork));
                } catch (JSONException ex){
                    ex.printStackTrace();
                }
            } catch (TimeoutException ex){
                //Informs the user there is now or extremely slow internet connection
                Toast.makeText(MyApplication.getAppContext(), "Request Timeout", Toast.LENGTH_LONG);
            } catch (ExecutionException ex){
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        setContentView(R.layout.activity_maps);
        //Refresh Button
        refreshButton = (Button) findViewById(R.id.refreshButton);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        //Change the city button
        changeCityButton = (Button) findViewById(R.id.changeCityButton);
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts the main activity
                Intent intent = new Intent(MyApplication.getAppContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        //
        listViewButton = (Button) findViewById(R.id.listViewButton);
        listViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //starts the list activity and passes the hrefs of the relevant networks
                Intent intent = new Intent(MyApplication.getAppContext(), ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("hrefs", networksHrefs);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.infro_window, null);
                    //sets the marker info window content text
                    TextView titleTextView = (TextView) view.findViewById(R.id.markerTitleTextView);
                    TextView snippetTextView = (TextView) view.findViewById(R.id.markerSnippetTextView);

                    titleTextView.setText(marker.getTitle());
                    snippetTextView.setText(marker.getSnippet());

                    return view;
                }
            });
        }
        //draws the markers on the map
        for (int i = 0; i < networks.size(); i++){
            putMarkersForNetwork(networks.get(i));
        }
        //sets the map position according to the location of the first network in the list with 13.5 zoom
        goToLocationZoom(networks.get(0).getLocation().getLatitude(), networks.get(0).getLocation().getLongitude(), 13.5f);
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        //shows the Location of the User on the map
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        mLocationRequest.setInterval(10000);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    //Go to location with zoom
    private void goToLocationZoom(Double latitude, Double longitude, Float zoom){
        LatLng location = new LatLng(latitude, longitude);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(location, zoom);
        mMap.animateCamera(update);
    }

    //draws all the markers of the given network on the map
    private void putMarkersForNetwork(Network network){
        MarkerOptions markerOptions;
        for (int i = 0; i < network.getStations().size(); i++){
            markerOptions = new MarkerOptions()
                    .title(network.getStations().get(i).getName())
                    .snippet("Empty Slots: " + network.getStations().get(i).getEmpty_slots().toString()
                            + " \nFree Bikes: " + network.getStations().get(i).getFree_bikes().toString()
                            + " \nNetwork: " + network.getName())
                    .position(new LatLng(network.getStations().get(i).getLatitude(), network.getStations().get(i).getLongitude()));
            mMap.addMarker(markerOptions);
        }
    }

}

