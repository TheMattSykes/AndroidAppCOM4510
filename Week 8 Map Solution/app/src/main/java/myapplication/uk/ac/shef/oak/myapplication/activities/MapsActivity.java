/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.MyViewModel;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;
import myapplication.uk.ac.shef.oak.myapplication.LocationService;
import myapplication.uk.ac.shef.oak.myapplication.R;
import myapplication.uk.ac.shef.oak.myapplication.sensors.Accelerometer;
import myapplication.uk.ac.shef.oak.myapplication.sensors.Barometer;
import myapplication.uk.ac.shef.oak.myapplication.sensors.Temperature;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;

    private static AppCompatActivity activity;
    private static GoogleMap mMap;
    private static final int ACCESS_FINE_LOCATION = 123;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private Button mButtonEnd;
    private PendingIntent mLocationPendingIntent;
    private Barometer barometer;
    private Accelerometer accelerometer;
    private Temperature ambientTemp;
    private boolean firstStart;

    private LocationManager locManager;

    private MyViewModel photoViewModel;

    private List<ImageData> returnedPictureList = new ArrayList<>();

    public static AppCompatActivity getActivity() {
        return activity;
    }

    public static void setActivity(AppCompatActivity activity) {
        MapsActivity.activity = activity;
    }

    public static GoogleMap getMap() {
        return mMap;
    }

    protected TextView mWeatherTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firstStart = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setActivity(this);

        // Instantiate variables
        barometer = new Barometer(this);
        accelerometer = new Accelerometer(this, barometer);
        ambientTemp = new Temperature(this);

//        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Initialise Database
//        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
//                AppDatabase.class, "imagedb").build();


        mButtonEnd = (Button) findViewById(R.id.button_end);
        mButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationUpdates();
                accelerometer.stopAccelerometer();
                ambientTemp.stopTemperatureSensor();
                mButtonEnd.setEnabled(false);
                MainActivity.pathPoints.clear();
                // Switch back to the main activity and finishing this one
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });
        mButtonEnd.setEnabled(true);

        // required by Android 6.0 +
        checkPermissions(getApplicationContext());

        photoViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        initEasyImage();

        initLocations();

        mWeatherTextView = (TextView) findViewById(R.id.weather_text_view);

        int numberOfCameras = Camera.getNumberOfCameras();

        if (numberOfCameras > 0) {
            // the floating button that will allow us to get the images from the Gallery
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_map_camera);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EasyImage.openCamera(getActivity(), 0);
                }
            });
        }

        // the floating button that will allow us to get the images from the Gallery
        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_map_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });
    }

    /**
     * Initialise EasyImage
     */
    private void initEasyImage() {
        EasyImage.configuration(this)
                .setImagesFolderName("RouteImages")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(false)
                .setAllowMultiplePickInGallery(true);
    }

    /**
     * Add the selected images to the database so that they can be shown on the album grid
     * @param returnedPhotos
     */
    private void onPhotosReturned(List<File> returnedPhotos) {
        System.out.println("TEST COMPLETED: onPhotosReturned CALLED HERE!");
        returnedPictureList.addAll(getImageElements(returnedPhotos));

        if (returnedPhotos != null) {
            for (File file : returnedPhotos) {

                /// Get file location

                String loc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/RouteImages/" + file.getName();

                /// Convert file to Bitmap

                BitmapFactory.Options option = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(loc, option);

                /// Convert Bitmap to Byte Array

                int size = bitmap.getRowBytes() * bitmap.getHeight();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();
                bitmap.recycle();

                Calendar calendar = Calendar.getInstance();
                Date date = calendar.getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyy-mm-dd hh:mm:ss");
                String dateString = dateFormat.format(date);

                Location location = mCurrentLocation;

                double lat = mCurrentLocation.getLatitude();
                double lon = mCurrentLocation.getLongitude();


                ImageData imd = new ImageData("Image Title", "Image description", 0, bytes, lat, lon, dateString);

                photoViewModel.insert(imd);
            }
        }

//        // we tell the adapter that the data is changed and hence the grid needs
//        // refreshing
//        mAdapter.notifyDataSetChanged();
//        mRecyclerView.scrollToPosition(returnedPhotos.size() - 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
                onPhotosReturned(imageFiles);
            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {

            }
        });
    }

    /**
     * given a list of photos, it creates a list of images
     * @param returnedPhotos
     * @return
     */
    private List<ImageData> getImageElements(List<File> returnedPhotos) {
        List<ImageData> imageElementList= new ArrayList<>();
        for (File file: returnedPhotos){
//            Image element = new Image(file);
//            imageElementList.add(element);
        }
        return imageElementList;
    }

    /**
     * Initialise location updates
     */
    private void initLocations() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

            return;
        }
    }

    /**
     * Starting location updates
     */
    private void startLocationUpdates(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        mLocationPendingIntent = PendingIntent.getService(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Void> locationTask = mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationPendingIntent);
            if (locationTask != null) {
                locationTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (e instanceof ApiException) {
                            Log.w("MapsActivity", ((ApiException) e).getStatusMessage());
                        } else {
                            Log.w("MapsActivity", e.getMessage());
                        }
                    }
                });

                locationTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("MapsActivity", "restarting gps successful!");
                    }
                });


            }
        }
    }

    /**
     * it stops the location updates
     */
    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        mLocationPendingIntent.cancel();
    }

    /**
     * Resuming the activity and resetting the LocationRequest parameters
     */
    @Override
    protected void onResume() {
        super.onResume();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Starting location updates and ambient temperature/barometric pressure sensing
        if (firstStart) {
            startLocationUpdates(getApplicationContext());
            // Accelerometer also starts barometric pressure sensing (when movement is detected)
            accelerometer.startAccelerometerRecording();
            ambientTemp.startSensingTemperature();
            // Because you can't start sensing in the onCreate method, or the app will crash...
            firstStart = false;
        }
    }


    private Location mCurrentLocation;
    private String mLastUpdateTime;
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            mCurrentLocation = locationResult.getLastLocation();
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                            mLocationCallback, null /* Looper */);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    /**
     * Manipulates the map, once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

   // public boolean addPhoto(MenuItem item) {
   //     Intent intent = new Intent(MapsActivity.this, CameraActivity.class);
   //     startActivity(intent);

   //     return true;
   // }

    /**
     * check permissions are necessary starting from Android 6
     * if you do not set the permissions, the activity will simply not work and you will be probably baffled for some hours
     * until you find a note on StackOverflow
     * @param context the calling context
     */
    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    androidx.appcompat.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    androidx.appcompat.app.AlertDialog.Builder alertBuilder = new androidx.appcompat.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    androidx.appcompat.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }


        }
    }
}
