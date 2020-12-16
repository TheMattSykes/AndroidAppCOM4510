/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

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

import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;
import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

/**
 * NewVisitViewModel
 * ViewModel for managing new visits
 * Adds new visit to the database
 */
public class NewVisitViewModel extends AndroidViewModel {
    private final MainRepository mRepository;

    LiveData<List<VisitData>> visits;

    public NewVisitViewModel (Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MainRepository(application);
//        images = mRepository.getNumberData();
    }


    /**
     * getter for the live data
     * @return
     */
    public LiveData<List<VisitData>> getVisits() {
        if (visits == null) {
            visits = new MutableLiveData<List<VisitData>>();
        }
        return visits;
    }

    /**
     * Insert new visit into the database
     * @param visit
     */
    public void insert(VisitData visit) {
        mRepository.saveVisit(visit);
    }
}
