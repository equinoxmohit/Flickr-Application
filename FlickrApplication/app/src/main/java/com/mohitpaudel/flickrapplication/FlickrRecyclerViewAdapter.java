package com.mohitpaudel.flickrapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mohit Paudel on 12/9/2016.
 */

public class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrViewHolder> {
    private static final String TAG = "FlickrRecyclerViewAdapt";

    private List<Photo> photoList;
    private Context mContext;

    public FlickrRecyclerViewAdapter(Context context, List<Photo> photoList) {
        mContext = context;
        this.photoList = photoList;
    }

    @Override
    public int getItemCount() {
        return (((photoList.size() != 0) && (photoList != null)) ? photoList.size() : 0);
    }

    @Override
    public FlickrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);
        return new FlickrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        Log.d(TAG, "onBindViewHolder: The position is ---->" + position);
        Picasso.with(mContext).load(photo.getImage())
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(holder.thumbnail);

        holder.title.setText(photo.getTitle());
    }


    public void loadData(List<Photo> newPhoto) {
        photoList = newPhoto;
        notifyDataSetChanged();
    }

    public Photo getPhoto(int position) {
        return (((photoList.size() != 0) && (photoList != null)) ? photoList.get(position) : null);
    }

    static class FlickrViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail = null;
        private TextView title = null;

        public FlickrViewHolder(View itemView) {
            super(itemView);
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
