package com.easyapps.androidnetworking;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOCATION_SEPARATOR = " of ";
    private Context context;
    private LayoutInflater inflater;
    List<Earthquake> data = Collections.emptyList();
    Earthquake current;
    int currentPos = 0;


    public EarthquakeAdapter(Context context, List<Earthquake> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.earthquake_list_item, parent, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String primaryLocation;
        String locationOffset;

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder = (MyHolder) holder;
        Earthquake current = data.get(position);


        String originalLocation = current.getLocation();
        if (originalLocation.contains(LOCATION_SEPARATOR)) { //separating/spliting string
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = context.getString(R.string.near_the);
            primaryLocation = originalLocation;
        }
        myHolder.primaryLocationView.setText(primaryLocation);
        myHolder.dateView.setText(formatDate(new Date(current.getTimeInMilliseconds())));
        myHolder.magnitudeView.setText(formatMagnitude(current.getMagnitude()));
        myHolder.locationOffsetView.setText(locationOffset);


        GradientDrawable magnitudeCircle = (GradientDrawable)myHolder.magnitudeView.getBackground();

        int magnitudeColor = getMagnitudeColor(current.getMagnitude());

        magnitudeCircle.setColor(magnitudeColor);

    }
    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView magnitudeView;
        //TextView formattedMagnitude;
        TextView primaryLocationView;
        TextView locationOffsetView;
        TextView dateView;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);

            primaryLocationView = (TextView) itemView.findViewById(R.id.primary_location);
            dateView = (TextView) itemView.findViewById(com.easyapps.androidnetworking.R.id.date);
            magnitudeView = (TextView) itemView.findViewById(com.easyapps.androidnetworking.R.id.magnitude);
            locationOffsetView = (TextView) itemView.findViewById(R.id.location_offset);

        }

    }


    private int getMagnitudeColor(double magnitude) {    // switch statement for choosing background color
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude); //converting double to int
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(context, magnitudeColorResourceId);
    }

    private String formatMagnitude(double magnitude) {       // converting double to decimal formate of "0.0" and as a string
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private String formatDate(Date dateObject) {  // converting date obj to String date of formate "LLL dd, yyyy",date obj already contains
                                                    // time in milliseconds
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {        // converting date obj to String time of formate "h:mm a",,date obj already contains
                                                        // time in milliseconds
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
