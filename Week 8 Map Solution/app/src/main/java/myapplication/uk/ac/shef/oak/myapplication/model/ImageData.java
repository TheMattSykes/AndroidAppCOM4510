package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

import io.reactivex.annotations.NonNull;

/**
 * Image entity
 */
@Entity(tableName = "images")
public class ImageData {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int id=0;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "visitId")
    private int visitId;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    public byte[] image;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "time")
    private String time;

    public ImageData(String title, String description, int visitId,
                     byte[] image, double latitude, double longitude, String time) {
        this.title = title;
        this.description = description;
        this.visitId = visitId;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }

    @androidx.annotation.NonNull
    public int getId() {
        return id;
    }

    public void setId(@androidx.annotation.NonNull int id) {
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVisitId() {
        return visitId;
    }

    public void setVisitId(int visitId) {
        this.visitId = visitId;
    }

    public Bitmap getImage() {
        byte[] image = this.image;
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        return bmp;
    }

    public void setImage(Bitmap image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        this.image = byteArray;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double latitude) {
        this.longitude = longitude;
    }

    public String getTime(){
        return this.time;
    }

    public void setTime(String time){
        this.time = time;
    }
}
