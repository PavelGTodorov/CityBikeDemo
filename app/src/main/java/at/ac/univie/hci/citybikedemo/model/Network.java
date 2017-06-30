package at.ac.univie.hci.citybikedemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO Class that represents a network
 */
public class Network {

    private String id;
    private List<String> companies;
    private String href;
    private Location location;
    private String name;
    private List<Station> stations;

    public Network() {
        this.id = "";
        this.companies = new ArrayList<>();
        this.href = "";
        this.location = new Location();
        this.name = "";
        this.stations = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public Integer getAllEmptySlots() {
        int count = 0;
        for (int i = 0; i < this.getStations().size(); i++){
          if (this.getStations().get(i) != null) {
              count += this.getStations().get(i).getEmpty_slots();
          }
        }
        return count;
    }

    public Integer getAllFreeBikes() {
        int count = 0;
        for (int i = 0; i < this.getStations().size(); i++){
           if (this.getStations().get(i) != null) {
               count += this.getStations().get(i).getFree_bikes();
           }
        }
        return count;
    }

    public Double getPercentageBikesOnTheRoad(){
        Double allEmptySlots = Double.parseDouble(getAllEmptySlots().toString());
        Double allFreeBikes = Double.parseDouble(getAllFreeBikes().toString());
        return allEmptySlots/(allEmptySlots + allFreeBikes)*100;
    }

    @Override
    public String toString() {
        return "Network{" +
                "id='" + id + '\'' +
                ", companies=" + companies +
                ", href='" + href + '\'' +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", stations=" + stations +
                '}';
    }
}
