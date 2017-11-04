package com.easyapps.androidnetworking;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.easyapps.androidnetworking.jsonSerializedClasses.Feature;
import com.easyapps.androidnetworking.jsonSerializedClasses.JsonToJava;
import com.easyapps.androidnetworking.jsonSerializedClasses.Properties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private final String REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private RecyclerView recyclerView;
    private EarthquakeAdapter adapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        Log.i("flow", "inside class:" + EarthquakeActivity.class.getName());
        Log.i("flow", "onCreate: ");
        setRecyclerView();
//..................................................................................................
        if (isNetworkConnected()) {
            loadRecyclerViewData();
        } else {
            networkNotConnected();
        }
    }// onCreate closing bracket

    //...........................................
    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(com.easyapps.androidnetworking.R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EarthquakeActivity.this));
        //right now we have passed in an Empty list
        adapter = new EarthquakeAdapter(EarthquakeActivity.this, new ArrayList<Earthquake>());
        recyclerView.setAdapter(adapter);

    }

    //........................................................................................
    private void loadRecyclerViewData() {

        Log.i("flow", "inside class:" + EarthquakeActivity.class.getName());
        Log.i("flow", "loadRecyclerViewData: ");

//.... building a request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, REQUEST_URL,new ResponseListners(),new ResponseListners());
        RequestQueue requestQueue = Volley.newRequestQueue(this);

//as soon response is recived for a Stringrequest ,its callback methods are called.
        requestQueue.add(stringRequest);
    }

    //.................................................
    private boolean isNetworkConnected() {
        Log.i("flow", "inside class:" + EarthquakeActivity.class.getName());
        Log.i("flow", "isNetworkConnected: ");

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    //..................................................
    private void networkNotConnected() {
        Log.i("flow", "inside class:" + EarthquakeActivity.class.getName());
        Log.i("flow", "networkNotConnected: ");

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        // Update empty state with no connection error message
        mEmptyStateTextView.setText(R.string.no_internet_connection);

    }

    //............................................

    private class ResponseListners implements Response.Listener<String>,Response.ErrorListener {

        @Override
        public void onResponse(String response) {

            Log.i("flow", "inside class:" + EarthquakeActivity.class.getName());
            Log.i("flow", "onResponse: recieved response :");

            View loadingIndicator =findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            ArrayList<Earthquake> earthquakes = new ArrayList<>();
            //GsonBuilder will eventually create Gson obj. it is first used to change the configurations of
            //that Gson obj for example "don't convert innerClass to json etc..we are not change configuration in this code though"
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create(); //this will return a gson obj with configuration we set in gsonBuilder.

            JsonToJava jsonToJava;
            //second parameter is response type...in our case its Json object because we are storeing it in Json obj.
            Log.i("check", "program reached till here");
            jsonToJava = gson.fromJson(response, JsonToJava.class);

            List<Feature> featureList = jsonToJava.getFeatures();
            for (int i = 0; i < featureList.size(); i++) {
                Properties properties = featureList.get(i).getProperties();
                earthquakes.add(new Earthquake(properties.getMag(), properties.getPlace(),
                        properties.getTime(), properties.getUrl()));
            }
            adapter.updateData(earthquakes);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("flow", "onErrorResponse: error in response ");
        }
        //............
    }
}