package at.ac.univie.hci.citybikedemo.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import at.ac.univie.hci.citybikedemo.R;
import at.ac.univie.hci.citybikedemo.model.Station;

/**
 * A Class used by the ListActivity onCreate() method to show the stations being passed
 */
public class StationAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<Station> mStations;

    /**
     * StationAdapter Constructor
     * @param context The current context
     * @param stations The stations to be shown as List
     */
    public StationAdapter(Context context, ArrayList<Station> stations) {
        mContext = context;
        mStations = stations;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mStations.size();
    }

    @Override
    public Object getItem(int position) {
        return mStations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = mLayoutInflater.inflate(R.layout.list_row, parent, false);

        TextView titleTextView = (TextView) rowView.findViewById(R.id.rowTitleTextView);
        TextView emptySlotsTextView = (TextView) rowView.findViewById(R.id.rowEmptySlotsTextView);
        TextView freeBikesTextView = (TextView) rowView.findViewById(R.id.rowFreeBikesTextView);
        TextView networkTextView = (TextView) rowView.findViewById(R.id.rowNetworkTextView);

        Station station = (Station) getItem(position);

        titleTextView.setText(station.getName());
        emptySlotsTextView.setText("Empty Slots: " + station.getEmpty_slots().toString());
        freeBikesTextView.setText("Free Bikes: " + station.getFree_bikes().toString());
        networkTextView.setText("Network: " + station.getNetwork());

        return rowView;
    }

}
