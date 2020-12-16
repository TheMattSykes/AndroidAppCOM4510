package myapplication.uk.ac.shef.oak.myapplication;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.model.ImageDAO;
import myapplication.uk.ac.shef.oak.myapplication.model.ImageData;
import myapplication.uk.ac.shef.oak.myapplication.model.MainDatabase;
import myapplication.uk.ac.shef.oak.myapplication.model.SensorDAO;
import myapplication.uk.ac.shef.oak.myapplication.model.SensorData;
import myapplication.uk.ac.shef.oak.myapplication.model.VisitDAO;
import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

public class MainRepository extends ViewModel {
    private final ImageDAO imageDBDao;
    private final SensorDAO sensorDBDao;
    private final VisitDAO visitDBDao;

    public MainRepository(Application application) {
        MainDatabase db = MainDatabase.getDatabase(application);
        imageDBDao = db.imageDAO();
        sensorDBDao = db.sensorDAO();
        visitDBDao = db.visitDAO();
    }

    // Image Queries + LiveData
    public LiveData<List<ImageData>> getImages() {
        return imageDBDao.retrieveAllImages();
    }

    public LiveData<List<ImageData>> getImagesByVisit(int visitId) {
        return imageDBDao.retrieveVisitImages(visitId);
    }

    public LiveData<List<ImageData>> getImagesBySearch(String search) {
        return imageDBDao.retrieveImageSearch(search);
    }

    // Sensor Queries + LiveData
    public LiveData<List<SensorData>> getSensors(){
        return sensorDBDao.retrieveSensorData();
    }

    public LiveData<List<SensorData>> getSensorsByVisit(int visitId) {
        return sensorDBDao.retrieveVisitSensorData(visitId);
    }

    // Visit Queries + LiveData
    public LiveData<List<VisitData>> getVisits(){
        return visitDBDao.retrieveVisits();
    }


}
