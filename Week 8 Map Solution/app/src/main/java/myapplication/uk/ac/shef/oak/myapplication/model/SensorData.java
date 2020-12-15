package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import android.graphics.Bitmap;
import io.reactivex.annotations.NonNull;


@Entity(indices = {@Index(value = {"id"})})
public class SensorData {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    private int id=0;

    @androidx.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@androidx.annotation.NonNull int id) {
        this.id = id;
    }

}
