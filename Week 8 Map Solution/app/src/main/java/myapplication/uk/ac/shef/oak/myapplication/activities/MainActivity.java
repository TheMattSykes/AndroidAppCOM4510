/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */
package myapplication.uk.ac.shef.oak.myapplication.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.NewVisit;
import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.VisitElement;
import myapplication.uk.ac.shef.oak.myapplication.ui.home.VisitsViewModel;

public class MainActivity extends AppCompatActivity {
    private Activity activity;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView mRecyclerView;
    private List<VisitElement> visitsList = new ArrayList<>();

    private VisitsViewModel visitsViewModel;
    public static ArrayList<LatLng> pathPoints = new ArrayList<>();
    private Button mButtonResume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this;

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_visits, R.id.navigation_album, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        mButtonResume = (Button) findViewById(R.id.button_resume);
        mButtonResume.setVisibility(View.INVISIBLE);
        mButtonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Switch to the maps activity
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        // Set resume button visible if there is an unfinished path present
        if (pathPoints.size() > 0)
            mButtonResume.setVisibility(View.VISIBLE);
//
//        initData();

//        visitsAdapter = new VisitsAdapter(visitsList);
//        mRecyclerView.setAdapter(visitsAdapter);

    }

    /**
     * Resuming the activity and making the resume button visible if there is an unfinished path
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Make resume button visible if there is an unfinished path present
        if (pathPoints.size() > 0)
            mButtonResume.setVisibility(View.VISIBLE);
    }

    public boolean newVisit(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, NewVisit.class);
        startActivity(intent);

        return true;
    }
//
//    public Activity getActivity() {
//        return activity;
//    }
}
