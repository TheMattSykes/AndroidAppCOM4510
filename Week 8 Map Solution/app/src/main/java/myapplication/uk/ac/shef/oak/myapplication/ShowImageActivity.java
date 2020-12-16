/*
 * Copyright (c) 2017. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

public class ShowImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        Bundle b = getIntent().getExtras();
        int position=-1;
        if(b != null) {
            // this is the image position in the itemList
            position = b.getInt("position");
//            if (position!=-1){
//                ImageView imageView = (ImageView) findViewById(R.id.image);
//                Image element= MyAdapter.getItems().get(position);
//                if (element.image!=-1) {
//                    imageView.setImageResource(element.image);
//                } else if (element.file!=null) {
//                    Bitmap myBitmap = BitmapFactory.decodeFile(element.file.getAbsolutePath());
//                    imageView.setImageBitmap(myBitmap);
//                }
//            }

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
        Intent intent = new Intent(ShowImageActivity.this, CameraActivity.class);
        startActivity(intent);

        return true;
    }

}
