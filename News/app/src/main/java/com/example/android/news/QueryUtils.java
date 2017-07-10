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

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    public static ArrayList<custom> fetchData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem Closing inputStream", e);
        }
        ArrayList<custom> List = getNewsDetails(jsonResponse);
        return List;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error Creating Url", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
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

    private static String readFromStream(InputStream inputStream) throws IOException {
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

    public static ArrayList<custom> getNewsDetails(String newsjson) {
        if (TextUtils.isEmpty(newsjson)) {
            return null;
        }
        ArrayList<custom> news = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsjson);
            JSONObject c = baseJsonResponse.getJSONObject("response");
            JSONArray newsarray = c.getJSONArray("results");
            for (int i = 0; i < newsarray.length(); i++) {
                JSONObject current = newsarray.getJSONObject(i);
                String title = current.getString("webTitle");
                String url = current.getString("webUrl");
                String sectionname = current.getString("sectionName");
                custom newsection = new custom(title, url, sectionname);
                news.add(newsection);
            }

        } catch (JSONException e) {
            Log.e("QueryUtils.java", "Problem Parsing the result.", e);
        }
        return news;
    }
}
