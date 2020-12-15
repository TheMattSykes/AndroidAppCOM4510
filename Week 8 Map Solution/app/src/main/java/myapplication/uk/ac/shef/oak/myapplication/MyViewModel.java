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

public class MyViewModel extends AndroidViewModel {
    private final MyRepository mRepository;

    LiveData<List<Image>> images;

    public MyViewModel (Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MyRepository(application);
//        images = mRepository.getNumberData();
    }


    /**
     * getter for the live data
     * @return
     */
    LiveData<List<Image>> getImages() {
        if (images == null) {
            images = new MutableLiveData<List<Image>>();
        }
        return images;
    }
}