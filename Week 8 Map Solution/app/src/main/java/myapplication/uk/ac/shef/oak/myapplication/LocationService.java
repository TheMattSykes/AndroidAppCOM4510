/*
 * Copyright (c) 2018. This code has been developed by Martin Szemethy, based on code developed by
 * Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.Date;

import myapplication.uk.ac.shef.oak.myapplication.activities.MainActivity;
import myapplication.uk.ac.shef.oak.myapplication.activities.MapsActivity;

/**
 * LocationService for map use
 */
public class LocationService extends IntentService {
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private Polyline path;

    public LocationService(String name) {
        super(name);
    }

    public LocationService() {
        super("Location Intent");
    }

    private TextView mWeatherTextView;

    /**
     * called when a location is recognised
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationResult.hasResult(intent)) {
            LocationResult locResults = LocationResult.extractResult(intent);
            if (locResults != null) {
                for (Location location : locResults.getLocations()) {
                    if (location == null) continue;
                    //do something with the location
                    Log.i("New Location", "Current location: " + location);
                    mCurrentLocation = location;
                    mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                    LatLng currentPos = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    MainActivity.pathPoints.add(currentPos);
                    // check if the activity has not been closed in the meantime
                    if (MapsActivity.getActivity()!=null) {
                        // any modification of the user interface must be done on the UI Thread. The Intent Service is running
                        // in its own thread, so it cannot communicate with the UI.
                        MapsActivity.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    LatLng currentPos = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                    //mWeatherTextView.setText("Current weather:\n" + (Math.random() * 30 - 10) + "°");
                                    if (MapsActivity.getMap() != null) {
                                        // Clearing the map, so the polyline can be drawn again
                                        MapsActivity.getMap().clear();
                                        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE);
                                        path = MapsActivity.getMap().addPolyline(options);
                                        path.setPoints(MainActivity.pathPoints);
                                        // Adding a marker for the current position
                                        //MapsActivity.getMap().addMarker(new MarkerOptions().position(currentPos).title(mLastUpdateTime));
                                    }
                                    CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                                    // it centres the camera around the new location
                                    MapsActivity.getMap().moveCamera(CameraUpdateFactory.newLatLng(currentPos));
                                    // it moves the camera to the selected zoom
                                    MapsActivity.getMap().animateCamera(zoom);
                                } catch (Exception e) {
                                    Log.e("LocationService", "Error cannot write on map " + e.getMessage());
                                }
                            }
                        });
                    }
                }
            }

        }
    }
}