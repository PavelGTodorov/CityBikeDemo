package at.ac.univie.hci.citybikedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.ac.univie.hci.citybikedemo.model.NetworksRepository;
import at.ac.univie.hci.citybikedemo.utility.HttpRequest;
import at.ac.univie.hci.citybikedemo.utility.MyApplication;
import at.ac.univie.hci.citybikedemo.utility.Parser;

/**
 * The Main Activity of the app. This activity is shown to the user after launching
 */
public class MainActivity extends Activity {

    /**
     * AutoCompleteTextView filled with all the cities available
     */
    private AutoCompleteTextView citiesAutoCompleteTextView;

    /**
     *The local networks repository
     */
    private NetworksRepository networksRepository = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Connects the java object with the xml "object"
         */
        citiesAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.citiesAutoCompleteTextView);

        try {
            /*
             * Executes a HTTP Request. Waits up to 5 seconds for respond.
             * If there is no respond within 5 seconds, a TimeoutException is thrown
             */
            String jsonString = new HttpRequest().execute().get(5000, TimeUnit.MILLISECONDS);
            try {
                //gets the JSON Array called networks
                JSONArray jsonNetworks = new JSONObject(jsonString).getJSONArray("networks");
                networksRepository = new NetworksRepository();
                for (int i = 0; i < jsonNetworks.length(); i++){
                    //transforms the JSON Objects into Java Objects and fills the networksRepository
                    networksRepository.getNetworks().add(Parser.parseReducedNetwork(jsonNetworks.getJSONObject(i)));
                }
                //creates a list with with the city names
                List<String> cities = networksRepository.getCityNames();
                ArrayAdapter citiesAdapter = new ArrayAdapter(this, R.layout.text, cities);
                citiesAutoCompleteTextView.setAdapter(citiesAdapter);
                //On click of a city the hrefs of the networks for that city are passed to the maps activity
                citiesAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ArrayList<String> networksHrefs = (ArrayList<String>) NetworksRepository.selectNetworksHrefsForCity(networksRepository.getNetworks(), (String) parent.getItemAtPosition(position));
                        Intent intent = new Intent(MyApplication.getAppContext(), MapsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("hrefs", networksHrefs);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }
            catch (JSONException ex){
                ex.printStackTrace();
            }

        } catch (TimeoutException ex){
            //Informs the user there is now or extremely slow internet connection
            Toast.makeText(MyApplication.getAppContext(), "Request Timeout", Toast.LENGTH_LONG);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}
