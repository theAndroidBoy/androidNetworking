package com.easyapps.androidnetworking;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> { //always use AsyncTaskLoader from support library otherwise you will get massive error

    private String mUrl;

//..............constructor...............................................
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;

        Log.i("flow","inside Constructor:"+ EarthquakeLoader.class.getName());

    }

//..........................................
    @Override
    protected void onStartLoading() {
        Log.i("flow","inside class:"+ EarthquakeLoader.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        forceLoad();
    }
//.......................................................
    @Override
    public List<Earthquake> loadInBackground() {
        Log.i("flow","inside class:"+ EarthquakeLoader.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        if (mUrl==null) {
            return null;
        }
        List<Earthquake> result = Utils.fetchDataFromUrl(mUrl);
        return result;
    }
//..................................................
}