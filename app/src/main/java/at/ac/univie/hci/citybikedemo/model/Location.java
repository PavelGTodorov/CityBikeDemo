package at.ac.univie.hci.citybikedemo.model;

/**
 * Simple POJO Class that represents the location of a network
 */
public class Location {

    private String country;
    private String city;
    private Double latitude;
    private Double longitude;

    public Location() {
        this.country = "";
        this.city = "";
        this.latitude = 0.0;
        this.longitude = 0.0;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    @Override
    public String toString() {
        return "Location{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
