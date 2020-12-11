/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MyRepository extends ViewModel {
    private final ImageDao mDBDao;

    public MyRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mDBDao = db.imageDao();
    }

    /**
     * it gets the data when changed in the db and returns it to the
     ViewModel
     * @return
     */
    public LiveData<Image> getImages() {
        return mDBDao.getAll();
    }

    private static class insertAsyncTask extends AsyncTask<Image, Void, Void> {
        private ImageDao mAsyncTaskDao;
        private LiveData<Image> images;

        insertAsyncTask(ImageDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Image... params) {
            mAsyncTaskDao.insert(params[0]);
//            Log.i("MyRepository", "number generated: "+params[0].getNumber()+"");
            return null;
        }
    }
}
