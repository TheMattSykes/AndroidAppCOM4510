/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.ViewModel;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.Image;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;

/**
 * View Model for getting images from the database
 */
public class MyViewModel extends AndroidViewModel {
    private final MainRepository mRepository;

    LiveData<List<ImageData>> images;

    public MyViewModel (Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MainRepository(application);
//        images = mRepository.getNumberData();
    }


    /**
     * getter for the live data
     * @return
     */
    public LiveData<List<ImageData>> getImages() {
        if (images == null) {
            images = new MutableLiveData<List<ImageData>>();
        }
        return images;
    }

    public void insert(ImageData image) {
        mRepository.saveImage(image);
    }
}