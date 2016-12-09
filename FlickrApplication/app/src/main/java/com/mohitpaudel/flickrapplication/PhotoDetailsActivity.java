package com.mohitpaudel.flickrapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_details);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        activateToolbar(true);

       /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        Intent intent = getIntent();
        Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
        if (photo != null) {
            TextView tvTitle = (TextView) findViewById(R.id.tvTitle);
            tvTitle.setText(photo.getTitle());

            TextView tvTags = (TextView) findViewById(R.id.tvTags);
            tvTags.setText(photo.getTags());

            TextView tvAuthor = (TextView) findViewById(R.id.tvAuthor);
            tvAuthor.setText(photo.getAuthor());

            ImageView tvImage = (ImageView) findViewById(R.id.tvImage);
            Picasso.with(this).load(photo.getLink())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(tvImage);

        }
    }


}
