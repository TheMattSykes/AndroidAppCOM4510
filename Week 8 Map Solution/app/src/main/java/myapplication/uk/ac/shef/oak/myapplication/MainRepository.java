package myapplication.uk.ac.shef.oak.myapplication;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

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

    // doesn't work >:c
    public void seedImages(){
        ImageView imageView = new ImageView(null);
        imageView.setImageResource(R.drawable.joe1);
        Bitmap bitmap1 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        imageView.setImageResource(R.drawable.joe2);
        Bitmap bitmap2 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        imageView.setImageResource(R.drawable.joe3);
        Bitmap bitmap3 = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        this.saveImage("joe1", "description of joe1", 1, bitmap1,
                0.0, 0.0, "placeholder time");
        this.saveImage("joe2", "description of joe2", 2, bitmap2,
                0.0, 0.0, "placeholder time");
        this.saveImage("joe3", "description of joe3", 3, bitmap3,
                0.0, 0.0, "placeholder time");
    }

    // Image Insert functions
    public void saveImage(String title, String description, int visitId,
                          Bitmap image, double lat, double lon, String time){
        ImageData imageData = new ImageData(title, description, visitId, null, lat, lon, time);
        imageData.setImage(image);
        new insertImageAsyncTask(imageDBDao).execute(imageData);
    }

    private static class insertImageAsyncTask extends AsyncTask<ImageData, Void, Void> {
        private ImageDAO mAsyncTaskDao;
        private LiveData<ImageData> imageData;

        insertImageAsyncTask(ImageDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ImageData... imageData) {
            mAsyncTaskDao.insert(imageData[0]);
            Log.i("MainRepository", "Image saved:"+imageData[0].getTitle()+"");
            return null;
        }
    }

    // Visit Insert functions
    public void saveVisit(String title, String time){
        new insertVisitAsyncTask(visitDBDao).execute(new VisitData(title, time));
    }

    private static class insertVisitAsyncTask extends AsyncTask<VisitData, Void, Void> {
        private VisitDAO mAsyncTaskDao;
        private LiveData<VisitData> visitData;

        insertVisitAsyncTask(VisitDAO dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(VisitData... visitData) {
            mAsyncTaskDao.insert(visitData[0]);
            return null;
        }
    }

    // Sensor Insert functions
    public void saveSensor(int visitId, String geolocation, String barometer,
                           String temperature, String time) {
        new insertSensorAsyncTask(sensorDBDao).execute(
                new SensorData(visitId, geolocation, barometer, temperature, time));
    }

    private static class insertSensorAsyncTask extends AsyncTask<SensorData, Void, Void> {
        private SensorDAO mAsyncTaskDao;
        private LiveData<SensorData> sensorData;

        insertSensorAsyncTask(SensorDAO dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SensorData... sensorData) {
            mAsyncTaskDao.insert(sensorData[0]);
            return null;
        }
    }
}
