package com.mohitpaudel.flickrapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mohit Paudel on 12/9/2016.
 */

enum DownloadStatus {
    IDLE, NOT_INITIALISED, PROCESSING, FAILED_OR_ERROR, OK
};


public class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    private DownloadStatus mDownloadStatus;
    private OnDownloadData mCallBack;

    interface OnDownloadData {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadData mCallBack) {
        mDownloadStatus = DownloadStatus.IDLE;
        this.mCallBack = mCallBack;
    }

    public void runOnSameThread(String s) {
        if (mCallBack != null) {
            mCallBack.onDownloadComplete(doInBackground(s), mDownloadStatus);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();

        if (params == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }
        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while (null != (line = reader.readLine())) {
                    result.append(line).append("\n");
                }
            }
            mDownloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch (MalformedURLException ex) {
            Log.e(TAG, "doInBackground: Malformedurlexception" + ex.getLocalizedMessage());
        } catch (IOException ex) {
            Log.e(TAG, "doInBackground: The IOException is " + ex.getLocalizedMessage());
        } catch (SecurityException ex) {
            Log.e(TAG, "doInBackground: The security exception that occured is " + ex.getLocalizedMessage());
        }
        mDownloadStatus = DownloadStatus.FAILED_OR_ERROR;
        return null;

    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mCallBack != null) {
            mCallBack.onDownloadComplete(s, mDownloadStatus);
        }
    }
}
