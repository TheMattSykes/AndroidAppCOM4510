/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Temperature
 */
public class Temperature {
    private static final String TAG = Temperature.class.getSimpleName();
    private long mSamplingRateInMSecs;
    private long mSamplingRateNano;
    private SensorEventListener mTemperatureListener = null;
    private SensorManager mSensorManager;
    private Sensor mTemperatureSensor;
    private long timePhoneWasLastRebooted = 0;
    private long TEMPERATURE_READING_FREQUENCY= 20000;
    private long lastReportTime = 0;
    private boolean started;

    public Temperature(Context context) {
        // http://androidforums.com/threads/how-to-get-time-of-last-system-boot.548661/
        timePhoneWasLastRebooted = System.currentTimeMillis() - SystemClock.elapsedRealtime();

        mSamplingRateNano = (long) (TEMPERATURE_READING_FREQUENCY) * 1000000;
        mSamplingRateInMSecs = (long) TEMPERATURE_READING_FREQUENCY;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mTemperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        initTemperatureListener();
    }

    /**
     * it inits the listener and establishes the actions to take when a reading is available
     */
    private void initTemperatureListener() {
        if (!ambientTemperatureAvailable()) {
            Log.d(TAG, "Ambient Temperature unavailable");
        } else {
            Log.d(TAG, "Using Temperature sensor");
            mTemperatureListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    long diff = event.timestamp - lastReportTime;
                    // time is in nanoseconds it represents the set reference times the first time we come here
                    // set event timestamp to current time in milliseconds
                    // see answer 2 at http://stackoverflow.com/questions/5500765/accelerometer-sensorevent-timestamp
                    // the following operation avoids reporting too many events too quickly - the sensor may always
                    // misbehave and start sending data very quickly
                    if (diff >= mSamplingRateNano) {
                        long actualTimeInMseconds = timePhoneWasLastRebooted + (long) (event.timestamp / 1000000.0);
                        float temperatureValue = event.values[0];
                        int accuracy = event.accuracy;

                        Log.i(TAG, Utilities.mSecsToString(actualTimeInMseconds) + ": current ambient temperature: " + temperatureValue + "with accuracy: " + accuracy);
                        lastReportTime = event.timestamp;
                    }
                }
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
        }

    }


    /**
     * it returns true if the sensor is available
     * @return
     */
    public boolean ambientTemperatureAvailable() {
        return (mTemperatureSensor != null);
    }

    /**
     * it starts the ambient temperature monitoring
     */
    public void startSensingTemperature() {
        // if the sensor is null,then mSensorManager is null and we get a crash
        if (ambientTemperatureAvailable()) {
            Log.d("Ambient Temp sensor", "starting listener");
            // delay is in microseconds (1millisecond=1000 microseconds)
            mSensorManager.registerListener(mTemperatureListener, mTemperatureSensor, (int) (mSamplingRateInMSecs * 1000));
            setStarted(true);
        } else {
            Log.i(TAG, "barometer unavailable or already active");
        }
    }


    /**
     * this stops the temperature sensor
     */
    public void stopTemperatureSensor() {
        if (ambientTemperatureAvailable()) {
            Log.d("Ambient Temperature", "Stopping listener");
            try {
                mSensorManager.unregisterListener(mTemperatureListener);
            } catch (Exception e) {
                // probably already unregistered
            }
        }
        setStarted(false);
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
