package at.ac.univie.hci.citybikedemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The methods of this class are imitating database queries
 */
public class NetworksRepository {

    private List<Network> networks;

    public NetworksRepository() {
        this.networks = new ArrayList<>();
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public List<String> getCityNames(){
        List<String> cities = new ArrayList<>();
        for (int i = 0; i < this.getNetworks().size(); i++){
            Boolean duplicate = false;
            for (int j = 0; j < cities.size(); j++){
                if (this.getNetworks().get(i).getLocation().getCity().equals(cities.get(j))) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate)
                cities.add(this.getNetworks().get(i).getLocation().getCity());
        }
        return cities;
    }

    public static List<String> selectNetworksHrefsForCity(List<Network> allNetworks, String city){
        List<String> networksHrefsForCity = new ArrayList<>();
        for (int i = 0; i < allNetworks.size(); i++){
            if (allNetworks.get(i).getLocation().getCity().equals(city)){
                networksHrefsForCity.add(allNetworks.get(i).getHref());
            }
        }
        return networksHrefsForCity;
    }

}

