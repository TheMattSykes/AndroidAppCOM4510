package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface ImageDAO {
    @Insert
    void insertAll(ImageData... imageData);

    @Insert
    void insert(ImageData imageData);

    @Delete
    void delete(ImageData imageData);

    @Delete
    void deleteAll(ImageData... imageData);

    @Query("SELECT COUNT(*) FROM imageData")
    int howManyElements();

    @Query("SELECT * FROM imageData")
    LiveData<ImageData> retrieveImages();
}
