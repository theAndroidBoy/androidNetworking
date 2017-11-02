package com.easyapps.androidnetworking;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private RecyclerView recyclerView;
    private EarthquakeAdapter adapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //----extras-----------
        Log.i("flow","inside class:"+ EarthquakeActivity.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        recyclerView = (RecyclerView) findViewById(com.easyapps.androidnetworking.R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EarthquakeActivity.this));
        //right now we have passed in an Empty list
        adapter = new EarthquakeAdapter(EarthquakeActivity.this, new ArrayList<Earthquake>());
        recyclerView.setAdapter(adapter);

// check if their is internet connection of not
        //getSystemService is a function inside Context class and CONNECTIVITY_SERVICE is a String constant inside Context class.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager(); //every activity comes with loadManager
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this); // second parameter is bundle of inputs,third is callback object which in our case is this.
            // this will start loader with id "EARTHQUAKE_LOADER_ID", if not present then call methods in object given in third parameter is called.

        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView= (TextView) findViewById(R.id.empty_view);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }

//......................Loader callBacks................................
@Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
    Log.i("flow","inside class:"+ EarthquakeActivity.class.getName());
    Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

         return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> data) {

        Log.i("flow","inside class:"+ EarthquakeActivity.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (data == null) {
            return;
        }

        if (data != null && !data.isEmpty()) {

            adapter.updateData((ArrayList)data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i("flow","inside class:"+ EarthquakeActivity.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

    adapter.clearAdapterData();
    }
//........................................................................................
}