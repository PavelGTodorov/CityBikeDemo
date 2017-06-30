package at.ac.univie.hci.citybikedemo;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.ac.univie.hci.citybikedemo.model.Network;
import at.ac.univie.hci.citybikedemo.model.Station;
import at.ac.univie.hci.citybikedemo.utility.HttpRequest;
import at.ac.univie.hci.citybikedemo.utility.MyApplication;
import at.ac.univie.hci.citybikedemo.utility.Parser;
import at.ac.univie.hci.citybikedemo.utility.StationAdapter;

public class ListActivity extends Activity {

    private ListView stationsListView;
    private Button refreshButton;
    private Button mapViewButton;
    private Button changeCityButton;

    private List<Network> networks;
    private ArrayList<Station> stations;
    private ArrayList<String> networksHrefs;
    private StationAdapter stationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networks = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        networksHrefs = bundle.getStringArrayList("hrefs");
        for (int i = 0; i < networksHrefs.size(); i++){
            try {
                String responseBody = new HttpRequest(networksHrefs.get(i)).execute().get(10000, TimeUnit.MILLISECONDS);
                try {
                    JSONObject jsonNetwork = new JSONObject(responseBody).getJSONObject("network");
                    Network currentNetwork = Parser.parseNetwork(jsonNetwork);
                    networks.add(currentNetwork);
                } catch (JSONException ex){
                    ex.printStackTrace();
                }
            } catch (TimeoutException ex){
                Toast.makeText(MyApplication.getAppContext(), "Request Timeout", Toast.LENGTH_LONG);
                ex.printStackTrace();
            } catch (ExecutionException ex){
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        setContentView(R.layout.activity_list);

        refreshButton = (Button) findViewById(R.id.refreshButton1);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });
        mapViewButton = (Button) findViewById(R.id.mapViewButton);
        mapViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getAppContext(), MapsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("hrefs", networksHrefs);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        changeCityButton = (Button) findViewById(R.id.changeCityButton1);
        changeCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplication.getAppContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        stationsListView = (ListView) findViewById(R.id.stationsListView);

        stations = new ArrayList<>();
        for (int i = 0; i < networks.size(); i++){
            for (int j = 0; j  < networks.get(i).getStations().size(); j++) {
                Station stationReady = networks.get(i).getStations().get(j);
                stationReady.setNetwork(networks.get(i).getName());
                stations.add(stationReady);
            }
        }
        stationAdapter = new StationAdapter(MyApplication.getAppContext(), stations);

        stationsListView.setAdapter(stationAdapter);
    }
}
