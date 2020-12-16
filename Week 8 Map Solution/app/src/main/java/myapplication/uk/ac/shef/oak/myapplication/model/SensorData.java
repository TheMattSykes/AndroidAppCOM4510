package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;
import io.reactivex.annotations.NonNull;

/**
 * Entity for Sensors
 */
@Entity(tableName = "sensors")
public class SensorData {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int id=0;

    @ColumnInfo(name = "visitId")
    private int visitId;

    @ColumnInfo(name = "geolocation")
    private String geolocation;

    @ColumnInfo(name = "barometer")
    private String barometer;

    @ColumnInfo(name = "temperature")
    private String temperature;

    @ColumnInfo(name = "time")
    private String time;

    public SensorData(int visitId, String geolocation, String barometer,
                      String temperature, String time) {
        this.visitId = visitId;
        this.geolocation = geolocation;
        this.barometer = barometer;
        this.temperature = temperature;
        this.time = time;
    }

    @androidx.annotation.NonNull
    public int getId() {
        return this.id;
    }
    public void setId(@androidx.annotation.NonNull int id) {
        this.id = id;
    }
    public int getVisitId() {
        return this.visitId;
    }
    public void setVisitId(int visitId) {
        this.visitId = visitId;
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
    public String getTime() {
        return this.time;
    }
    public void setTime(String time){
        this.time = time;
    }
}
