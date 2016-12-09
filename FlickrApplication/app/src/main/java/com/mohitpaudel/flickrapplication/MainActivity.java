package com.mohitpaudel.flickrapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements GetFlickrJsonData.OnDownloadComplete, RecyclerViewListener.RecyclerTouchListener {
    private static final String TAG = "MainActivity";
    private FlickrRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        activateToolbar(false);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new RecyclerViewListener(this, recyclerView, this));
        recyclerViewAdapter = new FlickrRecyclerViewAdapter(this, new ArrayList<Photo>());

        recyclerView.setAdapter(recyclerViewAdapter);

        /*GetRawData rawData = new GetRawData(this);
        rawData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,nougat&format=json&nojsoncallback=1&lang=en-us&tagmode=any");*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetFlickrJsonData jsonData = new GetFlickrJsonData(this, "https://api.flickr.com/services/feeds/photos_public.gne", "en-us", true);
        jsonData.execute("android,nougat");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDownloadComplete(List<Photo> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            //  Log.d(TAG, "onDownloadComplete: The data downloaded is:\n" + data);
            recyclerViewAdapter.loadData(data);
        } else {
            Log.d(TAG, "onDownloadComplete: The download status is\t" + status);
        }
    }

    @Override
    public void onItemLongTouch(View view, int position) {
        Log.d(TAG, "onItemLongTouch: starts");
        //Toast.makeText(MainActivity.this, "on item long touch", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, PhotoDetailsActivity.class);
        intent.putExtra(PHOTO_TRANSFER,recyclerViewAdapter.getPhoto(position));
        startActivity(intent);
        Log.d(TAG, "onItemLongTouch: Ends");
    }

    @Override
    public void onItemTouch(View view, int position) {
        Log.d(TAG, "onItemTouch: starts");
        Toast.makeText(MainActivity.this, "on item touch", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onItemTouch: ends");

    }
}
