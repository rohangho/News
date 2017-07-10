package com.example.android.news;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ROHAN on 08-07-2017.
 */

public class Loaders extends AsyncTaskLoader<ArrayList<custom>> {
   /**Tag for Log message*/
    private static final String LOG_TAG = Loaders.class.getSimpleName();
    /** Query URL */
    private String mUrl;
    public Loaders(Context context, String url) {
        super(context);
        mUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public ArrayList<custom> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        ArrayList<custom> Earth = QueryUtils.fetchData(mUrl);
        return Earth;
    }
}