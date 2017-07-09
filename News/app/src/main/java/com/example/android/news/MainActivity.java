package com.example.android.news;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.example.android.news.R.drawable.a;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<custom>> {


    private static final String USGS_REQUEST_URL = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=65b393d26d7a49d9aed3e1950347f1c0";
    private Adapter mAdapter;

     TextView mEmptyStateTextView;

    private Adapter news;
    private static final int id = 1;
    ProgressBar loading_Indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView newsListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView)findViewById(R.id.Empty_textview);
        newsListView.setEmptyView(mEmptyStateTextView);

        loading_Indicator = (ProgressBar)findViewById(R.id.progress_bar);

        mAdapter = new Adapter(this, new ArrayList<custom>());
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {@Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            custom current = mAdapter.getItem(position);
            Uri currentnewsUri = Uri.parse(current.getUrl());
            Intent launchUrl = new Intent(Intent.ACTION_VIEW, currentnewsUri);
            startActivity(launchUrl);

        }
        });

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo !=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(id, null, this);
        }

        else{
            View loadingIndicator = findViewById(R.id.progress_bar);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);

        }
    }




    @Override
    public Loader<ArrayList<custom>> onCreateLoader(int id, Bundle args) {


        return new Loaders(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<custom>> loader, ArrayList<custom> news) {
       mEmptyStateTextView.setText("No data foundd");
        loading_Indicator.setVisibility(View.GONE);
        mAdapter.clear();
        if (news!=null && !news.isEmpty()){
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<custom>> loader) {
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, settings_activity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}







