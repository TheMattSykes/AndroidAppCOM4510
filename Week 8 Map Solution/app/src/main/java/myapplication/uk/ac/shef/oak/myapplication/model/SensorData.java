package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;
import io.reactivex.annotations.NonNull;


@Entity(tableName = "sensors")
public class SensorData {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int id=0;

    @ColumnInfo(name = "geolocation")
    private String geolocation;

    @ColumnInfo(name = "barometer")
    private String barometer;

    @ColumnInfo(name = "temperature")
    private String temperature;

    @androidx.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@androidx.annotation.NonNull int id) {
        this.id = id;
    }
    public String getGeolocation(){
        return this.geolocation;
    }
    public void setGeolocation(String geolocation){
        this.geolocation = geolocation;
    }
    public String getBarometer(){
        return this.barometer;
    }
    public void setBarometer(String barometer){
        this.barometer = barometer;
    }
    public String getTemperature(){
        return this.temperature;
    }
    public void setTemperature(String temperature){
        this.temperature = temperature;
    }
}
