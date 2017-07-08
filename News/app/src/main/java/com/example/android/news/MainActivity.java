package com.example.android.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.news.R.drawable.a;

public class MainActivity extends AppCompatActivity {


    private static final String USGS_REQUEST_URL ="https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=65b393d26d7a49d9aed3e1950347f1c0";
    private Adapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.Empty_textview);
        listView.setEmptyView(mEmptyStateTextView);

        mAdapter = new Adapter(this, new ArrayList<custom>());
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            listView.setAdapter(mAdapter);
            bookAsyncTask task = new bookAsyncTask();

            task.execute(USGS_REQUEST_URL);
        } else {
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
    }

    private class bookAsyncTask extends AsyncTask<String, Void, List<custom>> {
        @Override
        protected List<custom> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;

            }
            List<custom> result = QueryUtils.fetchData(urls[0]);
            return result;
        }
        @Override
        protected void onPostExecute(List<custom> data) {
            mAdapter.clear();
            if (data != null && !data.isEmpty()) {
                mEmptyStateTextView.setText(R.string.no_data_found);
                mAdapter.addAll(data);
            }
        }
    }
}






