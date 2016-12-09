package com.mohitpaudel.flickrapplication;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Mohit Paudel on 12/9/2016.
 */

public class RecyclerViewListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerViewListener";
    private RecyclerTouchListener mRecyclerTouchListener;
    private GestureDetectorCompat mGestureDetector;


    interface RecyclerTouchListener {
        void onItemTouch(View view, int position);

        void onItemLongTouch(View view, int position);
    }

    public RecyclerViewListener(Context context, final RecyclerView recyclerView, RecyclerTouchListener recyclerTouchListener) {
        mRecyclerTouchListener = recyclerTouchListener;
        mGestureDetector = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mRecyclerTouchListener != null) {
                    mRecyclerTouchListener.onItemTouch(childView, recyclerView.getChildAdapterPosition(childView));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && mRecyclerTouchListener != null) {
                    mRecyclerTouchListener.onItemLongTouch(childView, recyclerView.getChildAdapterPosition(childView));
                }

            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector != null) {
            boolean result = mGestureDetector.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent(): returns" + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent(): returns false");
            return false;
        }


    }
}
