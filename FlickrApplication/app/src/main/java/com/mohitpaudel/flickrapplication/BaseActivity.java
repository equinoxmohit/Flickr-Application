package com.mohitpaudel.flickrapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by Mohit Paudel on 12/9/2016.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public static final String FLICKR_QUERY = "FLICKR_QUERY";
    public static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";

    void activateToolbar(boolean enableHome) {
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(enableHome);
        }
        Log.d(TAG, "activateToolbar: ends");
    }
}
