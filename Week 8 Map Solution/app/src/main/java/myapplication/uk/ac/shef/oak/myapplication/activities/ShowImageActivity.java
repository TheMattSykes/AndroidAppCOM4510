/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.AlbumAdapter;
import myapplication.uk.ac.shef.oak.myapplication.AlbumViewModel;
import myapplication.uk.ac.shef.oak.myapplication.MainRepository;
import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;

public class ShowImageActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        Bundle b = getIntent().getExtras();
        int position=-1;
        if(b != null) {
            position = b.getInt("position");
            // this is the image position in the itemList
            if (position!=-1){
                ImageView imageView = (ImageView) findViewById(R.id.image);
                TextView textView = (TextView) findViewById(R.id.text);
                ImageData element = AlbumAdapter.getItems().get(position);
                if (element != null) {
                    imageView.setImageBitmap(element.getImage());
                    String content = "Image ID: "+element.getId()+"\n"+
                            "Visit ID: "+element.getVisitId()+"\n"+
                            "Title: "+element.getTitle()+"\n"+
                            "Description: "+element.getDescription()+"\n"+
                            "Latitude: "+element.getLatitude()+
                            " and Longitude: "+element.getLongitude()+"\n"+
                            "Time: "+element.getTime();
                    textView.setText(content);
                }
            }

        }
    }

    /**
     * Override onCreateOptionsMenu to add an add icon to the top menu bar
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.topmenushowimage, menu);
        return true;
    }

    public boolean editMetadata(MenuItem item) {
        //Intent intent = new Intent(ShowImageActivity.this, CameraActivity.class);
        //startActivity(intent);

        return true;
    }

}
