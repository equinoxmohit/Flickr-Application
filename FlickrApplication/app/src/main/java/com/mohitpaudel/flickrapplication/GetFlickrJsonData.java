package com.mohitpaudel.flickrapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohit Paudel on 12/9/2016.
 */

public class GetFlickrJsonData extends AsyncTask<String, Void, List<Photo>> implements GetRawData.OnDownloadData {
    private static final String TAG = "GetFlickrJsonData";
    private List<Photo> photoList;
    private String baseUrl, lang;
    private boolean matchAll;
    private OnDownloadComplete mCallBack;

    public GetFlickrJsonData(OnDownloadComplete callBack, String baseUrl, String lang, boolean matchAll) {
        mCallBack = callBack;
        this.baseUrl = baseUrl;
        this.lang = lang;
        this.matchAll = matchAll;
        this.photoList = new ArrayList<>();
    }


    interface OnDownloadComplete {
        void onDownloadComplete(List<Photo> data, DownloadStatus status);
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        super.onPostExecute(photos);
        if (mCallBack != null) {
            mCallBack.onDownloadComplete(photos, DownloadStatus.OK);
        }

    }

    @Override
    protected List<Photo> doInBackground(String... params) {
        String downloadUri = createUri(params[0], lang, matchAll);

        GetRawData rawData = new GetRawData(this);
        rawData.runOnSameThread(downloadUri);

        return photoList;
    }

    private String createUri(String searchCriteria, String lang, boolean matchAll) {
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .appendQueryParameter("lang", lang)
                .appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonImage = itemsArray.getJSONObject(i);
                    String title = jsonImage.getString("title");
                    String author = jsonImage.getString("author");
                    String authorId = jsonImage.getString("author_id");
                    String tags = jsonImage.getString("tags");
                    JSONObject mediaObject = jsonImage.getJSONObject("media");
                    String image = mediaObject.getString("m");
                    String link = image.replace("_m.", "_b.");
                    Photo photo = new Photo(title, author, authorId, tags, image, link);
                    photoList.add(photo);
                }
            } catch (JSONException ex) {
                Log.e(TAG, "onDownloadComplete: The error while parsing is " + ex.getLocalizedMessage());
            }

        }

    }
}
