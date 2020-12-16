package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

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

    // Retrieve a count of all images
    @Query("SELECT COUNT(*) FROM images")
    int howManyElements();

    // Retrieve all of the images
    @Query("SELECT * FROM images")
    LiveData<List<ImageData>> retrieveAllImages();

    // Retrieve the images assigned to a specific visit
    @Query("SELECT * FROM images WHERE visitId = :visitId")
    LiveData<List<ImageData>> retrieveVisitImages(int visitId);

    // Retrieve images whose information are like a search query
    @Query("SELECT * FROM images WHERE title LIKE :query OR description LIKE :query")
    LiveData<List<ImageData>> retrieveImageSearch(String query);
}
