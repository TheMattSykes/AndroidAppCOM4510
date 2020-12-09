package myapplication.uk.ac.shef.oak.myapplication;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

public class SensorService extends IntentService {

    public SensorService(String name) { super(name); }
    public SensorService() {
        super("Sensor Intent");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
