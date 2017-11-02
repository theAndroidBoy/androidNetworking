/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.easyapps.androidnetworking;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//....................................................................
public final class Utils {


    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<Earthquake> fetchDataFromUrl(String requestUrl) {

        Log.i("flow","inside class:"+ Utils.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        // Create URL object
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Earthquake> earthquake =
                parsingJsontoMakeObjects(jsonResponse);
        return earthquake;
    }

//...............................................................................
    private static URL createUrl(String stringUrl) {
        Log.i("flow","inside class:"+ Utils.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

//.................................................................................
    private static String makeHttpRequest(URL url) throws IOException {
        Log.i("flow","inside class:"+ Utils.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            Log.i("check", "url is null" );
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 );
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            Log.i("check", "just before urlConnection.connect statement" );
            urlConnection.connect();
            Log.i("check", "just after urlConnection.connect statement" );

            if (urlConnection.getResponseCode() == 200) {
                Log.i("check", "urlConnectionResponseCode= "+urlConnection.getResponseCode() );
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

//...............................................................................................
    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.i("flow","inside class:"+ Utils.class.getName());
        Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

//.....................................................................................
public static ArrayList<Earthquake> parsingJsontoMakeObjects(String jsonResponse) {

    Log.i("flow","inside class:"+ Utils.class.getName());
    Log.i("flow","inside function:"+ new Object(){}.getClass().getEnclosingMethod().getName());

    ArrayList<Earthquake> earthquakes = new ArrayList<>();

    try {
        JSONObject baseJsonResponse = new JSONObject(jsonResponse);

        JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

        for (int i = 0; i < earthquakeArray.length(); i++) {   //Json parsing

            JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
            JSONObject properties = currentEarthquake.getJSONObject("properties");
            double magnitude = properties.getDouble("mag");
            String location = properties.getString("place");
            long time = properties.getLong("time");
            String url = properties.getString("url");
            Log.i("value", "extractEarthquakes: "+location);
            Earthquake earthquake = new Earthquake(magnitude, location, time, url);

            earthquakes.add(earthquake);
        }

    } catch (JSONException e) {

        Log.e("ParsingMethods", "Problem parsing the earthquake JSON results", e);
    }

    // Return the list of earthquakes
    return earthquakes;
}
}
