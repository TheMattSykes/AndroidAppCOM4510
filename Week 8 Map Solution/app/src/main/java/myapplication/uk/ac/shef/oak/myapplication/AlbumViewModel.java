/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;

/**
 * ViewModel for the Album Fragment
 * Gets a list of images
 */
public class AlbumViewModel extends AndroidViewModel {
    private final MainRepository mainRepository;

    LiveData<List<ImageData>> images;

    public AlbumViewModel(Application application) {
        super(application);
        // creation and connection to the Repository
        mainRepository = new MainRepository(application);
        images = mainRepository.getImages();
    }

    /**
     * getter for the live data
     * @return an updated list of images
     */
    public LiveData<List<ImageData>> getImages() {
        if (images == null) {
            images = new MutableLiveData<List<ImageData>>();
        }
        return images;
    }
}
