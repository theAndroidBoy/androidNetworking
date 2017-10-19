package com.easyapps.androidnetworking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Earthquake> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        listItems = ParsingMethods.extractEarthquakes();

        recyclerView = (RecyclerView) findViewById(com.easyapps.androidnetworking.R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new EarthquakeAdapter(this,listItems);

        recyclerView.setAdapter(adapter);

    }
}