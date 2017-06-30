package at.ac.univie.hci.citybikedemo.model;

/**
 * Simple POJO Class that represents a station
 */
public class Station {

    private String id;
    private Integer empty_slots;
    private Integer free_bikes;
    private Double latitude;
    private Double longitude;
    private String name;
    private String timestamp;
    private Extra extra;
    private String network;

    public Station() {
        this.id = "";
        this.empty_slots = 0;
        this.free_bikes = 0;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.name = "";
        this.timestamp = "";
        network = null;
        this.extra = new Extra();
    }


    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEmpty_slots() {
        return empty_slots;
    }

    public void setEmpty_slots(Integer empty_slots) {
        this.empty_slots = empty_slots;
    }

    public Integer getFree_bikes() {
        return free_bikes;
    }

    public void setFree_bikes(Integer free_bikes) {
        this.free_bikes = free_bikes;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Extra getExtra() {
        return extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "\nStation{" +
                "\nid='" + id + '\'' +
                "\n, empty_slots=" + empty_slots +
                "\n, free_bikes=" + free_bikes +
                "\n, latitude=" + latitude +
                "\n, longitude=" + longitude +
                "\n, name='" + name + '\'' +
                "\n, timestamp='" + timestamp + '\'' +
//              "\n, extra=" + extra +
                "\n}";
    }
}
