package com.example.android.news;

import android.text.TextUtils;
import android.util.Log;

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

/**
 * Created by admin on 31-07-2016.
 */
public final class QueryUtils {

    private QueryUtils() {
    }

    /**
     * Log Message tag
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Query the Dataset and fetch the list of Earthquakes
     */

    public static ArrayList<custom> fetchData(String requestUrl) {

        //Create Url Object
        URL url = createUrl(requestUrl);

        //perform httpRequest to the URl and Receive JSON Response
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem Closing inputStream", e);
        }

        //Extract earthquakes info from jsonResponse and create a List of EarthQuakes
        ArrayList<custom> EarthquakeList = getNewsDetails(jsonResponse);

        // Return the Earthquake List
        return EarthquakeList;
    }


    /**
     * Create URL Object from given string Url
     */

    private static URL createUrl(String stringUrl) {

        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error Creating Url", e);
        }
        return url;

    }

    /**
     * Make Http Request to the given Url and return Response as string
     */

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /*milliseconds*/);
            urlConnection.setConnectTimeout(15000 /*milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error Response Code : " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem Retrieving the json Response", e);
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

    /**
     * Converts the link into a String which contains
     * whole JSON Response recieved from server
     */
    private static String readFromStream(InputStream inputStream) throws IOException {

        // to store the json Response
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

    /**
     * Return the List Of Earthqakes from the jsonResponse;
     */

    public static ArrayList<custom> getNewsDetails(String newsjson) {

        // if the json Response is empty or null, then return
        if (TextUtils.isEmpty(newsjson)) {
            return null;
        }

        ArrayList<custom> news = new ArrayList<>();

        try {
            JSONObject baseJsonResponse=new JSONObject(newsjson);
            JSONArray newsarray=baseJsonResponse.getJSONArray("articles");
            for(int i=0;i<newsarray.length();i++){
                JSONObject current = newsarray.getJSONObject(i);
                String title=current.getString("title");
                String url=current.getString("url");
                String url_img=current.getString("urlToImage");
                custom books= new custom(url_img,title,url);
                news.add(books);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils.java", "Problem Parsing the result.", e);
        }

        return news;
    }
}
