package com.easyapps.androidnetworking;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private RecyclerView recyclerView;
    private EarthquakeAdapter adapter;
    private List<Earthquake> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        recyclerView = (RecyclerView) findViewById(com.easyapps.androidnetworking.R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EarthquakeActivity.this));
        //right now we have passed in an Empty list
        adapter = new EarthquakeAdapter(EarthquakeActivity.this, new ArrayList<Earthquake>());
        recyclerView.setAdapter(adapter);

        LoaderManager loaderManager = getSupportLoaderManager(); //every activity comes with loadManager
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this); // second parameter is bundle of inputs,third is callback object which in our case is this.
// this will start loader with id "EARTHQUAKE_LOADER_ID", if not present then call methods in object given in third parameter is called.
    }

//......................Loader callBacks................................
@Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
         return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {
        if (data == null) {
            return;
        }

        if (data != null && !data.isEmpty()) {

            adapter.updateData((ArrayList)data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
    adapter.clearAdapterData();
    }
//........................................................................................
}