package com.easyapps.androidnetworking;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Earthquake> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        recyclerView = (RecyclerView) findViewById(com.easyapps.androidnetworking.R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EarthquakeActivity.this));

        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }
//........................................................................................
    private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        protected List<Earthquake> doInBackground(String... urls) { // input of execute function in onCreate is actually input of this function
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Earthquake> result = Utils.fetchDataFromUrl(urls[0]);
            return result;
        }
//..............................................................
        protected void onPostExecute(List<Earthquake> result) { //result of doInBack is input of this function
            if (result == null) {
                return;
            }

            adapter = new EarthquakeAdapter(EarthquakeActivity.this, result);
            recyclerView.setAdapter(adapter);
        }
    }
//......................................................................................................

}