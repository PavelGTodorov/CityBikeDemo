package at.ac.univie.hci.citybikedemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO Class that represents the extra information relevent to a station.
 * Needs additional fields.
 */
public class Extra {

    private List<Integer> bike_uids;
    private Integer number;
    private Integer slots;

    private Integer uid;

    public Extra() {
        this.bike_uids = new ArrayList<>();
        this.number = 0;
        this.slots = 0;
        this.uid = 0;
    }

    public List<Integer> getBike_uids() {
        return bike_uids;
    }

    public void setBike_uids(List<Integer> bike_uids) {
        this.bike_uids = bike_uids;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSlots() {
        return slots;
    }

    public void setSlots(Integer slots) {
        this.slots = slots;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Extra{" +
                "bike_uids=" + bike_uids +
                ", number=" + number +
                ", slots=" + slots +
                ", uid=" + uid +
                '}';
    }
}
