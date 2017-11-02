package com.easyapps.androidnetworking;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> { //always use AsyncTaskLoader from support library otherwise you will get massive error

    private String mUrl;

//..............constructor...............................................
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

//..........................................
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
//.......................................................
    @Override
    public List<Earthquake> loadInBackground() {
        if (mUrl==null) {
            return null;
        }
        List<Earthquake> result = Utils.fetchDataFromUrl(mUrl);
        return result;
    }
//..................................................
}