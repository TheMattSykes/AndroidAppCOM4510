package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;
import io.reactivex.annotations.NonNull;

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

    @ColumnInfo(name = "image")
    @Ignore
    public Bitmap image;

    @ColumnInfo(name = "geolocation")
    private String geolocation;

    public ImageData(String title, String description, int visitId) {
        this.title = title;
        this.description = description;
        this.visitId = visitId;
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
        return image;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getGeolocation() {
        return this.geolocation;
    }
    public void setGeolocation(String geolocation){
        this.geolocation = geolocation;
    }
}
