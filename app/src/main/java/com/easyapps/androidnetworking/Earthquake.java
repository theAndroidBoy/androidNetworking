package com.easyapps.androidnetworking;

import android.util.Log;

public class Earthquake {

    private double mMagnitude;

    private String mLocation;

    private long mTimeInMilliseconds;

    private String mUrl;

    public Earthquake(double magnitude, String location, long timeInMilliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;

        Log.i("flow", "inside Constructor:" + Earthquake.class.getName());
        Log.i("flow", "Earthquake: ");


    }

    public double getMagnitude() {
        Log.i("flow", "inside class:" + Earthquake.class.getName());
        Log.i("flow", "getMagnitude: ");

        return mMagnitude;
    }

    public String getLocation() {
        Log.i("flow", "inside class:" + Earthquake.class.getName());
        Log.i("flow", "getLocation: ");

        return mLocation;
    }

    public long getTimeInMilliseconds() {
        Log.i("flow", "inside class:" + Earthquake.class.getName());
        Log.i("flow", "getTimeInMilliseconds: ");

        return mTimeInMilliseconds;
    }

    public String getUrl() {
        Log.i("flow", "inside class:" + Earthquake.class.getName());
        Log.i("flow", "getUrl: ");

        return mUrl;
    }
}
