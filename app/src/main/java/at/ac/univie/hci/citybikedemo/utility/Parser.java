package at.ac.univie.hci.citybikedemo.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import at.ac.univie.hci.citybikedemo.model.Extra;
import at.ac.univie.hci.citybikedemo.model.Location;
import at.ac.univie.hci.citybikedemo.model.Network;
import at.ac.univie.hci.citybikedemo.model.Station;

/**
 * Utility Class used for Parsing the JSON Objects to Java Objects
 */
public class Parser {

    /**
     * Parses the detailed version(the version with all the stations) of a network
     * @param jsonNetwork The network to be parsed as JSON Object
     * @return The network as Java Object or null if Exceptions is thrown
     */
    public static Network parseNetwork(JSONObject jsonNetwork){
        try {
            Network network = new Network();
            JSONArray jsonCompanies = jsonNetwork.getJSONArray("company");
            for (int i = 0; i < jsonCompanies.length(); i++ ){
                network.getCompanies().add(jsonCompanies.getString(i));
            }
            JSONObject jsonLocation = jsonNetwork.getJSONObject("location");
            JSONArray jsonStations = jsonNetwork.getJSONArray("stations");
            for (int i = 0; i < jsonStations.length(); i++){
                network.getStations().add(parseStation(jsonStations.getJSONObject(i)));
            }
            network.setId(jsonNetwork.getString("id"));
            network.setHref(jsonNetwork.getString("href"));
            network.setName(jsonNetwork.getString("name"));
            network.setLocation(parseLocation(jsonLocation));
            network.setCompanies(null);
            return network;
        }
        catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Parses a network without the stations from JSON Object to Java Object
     * @param jsonNetwork The network to be parsed as JSON Object
     * @return The network as Java Object or null if Exceptions is thrown
     */
    public static Network parseReducedNetwork(JSONObject jsonNetwork){
        try {
            Network network = new Network();
            JSONObject jsonLocation = jsonNetwork.getJSONObject("location");
            network.setId(jsonNetwork.getString("id"));
            network.setHref(jsonNetwork.getString("href"));
            network.setName(jsonNetwork.getString("name"));
            network.setLocation(parseLocation(jsonLocation));
            network.setStations(null);
            network.setCompanies(null);
            return network;
        }
        catch (JSONException ex){
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Parses the location of a network from JSON Object to Java Object
     * @param jsonLocation The location to be parsed as JSON Object
     * @return The location as Java Object or null if Exceptions is thrown
     */
    private static Location parseLocation(JSONObject jsonLocation){
        try {
            Location location = new Location();
            location.setCity(jsonLocation.getString("city"));
            location.setCountry(jsonLocation.getString("country"));
            location.setLatitude(jsonLocation.getDouble("latitude"));
            location.setLongitude(jsonLocation.getDouble("longitude"));
            return location;
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Parses a station of a network from JSON Object to Java Object
     * @param jsonStation The station to be parsed as JSON Object
     * @return The station as Java Object or null if exceptions is thrown
     */
    private static Station parseStation(JSONObject jsonStation){
        try{
            Station station = new Station();
            station.setLongitude(jsonStation.getDouble("longitude"));
            station.setLatitude(jsonStation.getDouble("latitude"));
            if (jsonStation.isNull("empty_slots")) {
                station.setEmpty_slots(0);
            }
            else {
                station.setEmpty_slots(Integer.parseInt(jsonStation.getString("empty_slots")));
            }
            if (jsonStation.isNull("free_bikes")){
                station.setFree_bikes(0);
            }
            else {
                station.setFree_bikes(jsonStation.getInt("free_bikes"));
            }
            station.setId(jsonStation.getString("id"));
            station.setName(jsonStation.getString("name"));
            station.setTimestamp(jsonStation.getString("timestamp"));
            station.setExtra(parseExtra(jsonStation.getJSONObject("extra")));
            return station;
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Parses the extra information of a station from JSON Object to Java Object
     * @param jsonExtra The extra information to be parses as JSON Object
     * @return The station as Java Object or null if exception is thrown
     */
    private static Extra parseExtra(JSONObject jsonExtra){
        Extra extra = new Extra();
        try {
            extra.setNumber(jsonExtra.getInt("number"));
        }catch (JSONException ex){
            extra.setNumber(null);
        }
        try {
            extra.setSlots(jsonExtra.getInt("slots"));
        }catch (JSONException ex){
            extra.setSlots(null);
        }
        try {
            extra.setUid(jsonExtra.getInt("uid"));
        }catch (JSONException ex){
            extra.setUid(null);
        }
        try {
            JSONArray jsonBikeUids = jsonExtra.getJSONArray("bike_uids");
            ArrayList<Integer> bikeUids = new ArrayList<>();
            for (int i = 0; i < jsonBikeUids.length(); i++){
                if (jsonBikeUids.get(i) != null){
                    bikeUids.add(jsonBikeUids.getInt(i));
                }
            }
            extra.setBike_uids(bikeUids);
        } catch (JSONException ex){
            extra.setBike_uids(null);
        }
        return extra;
    }
}
