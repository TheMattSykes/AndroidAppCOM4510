package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VisitDAO {
    @Insert
    void insertAll(VisitData... visitData);

    @Insert
    void insert(VisitData visitData);

    @Delete
    void delete(VisitData visitData);

    @Delete
    void deleteAll(VisitData... visitData);

    @Query("SELECT COUNT(*) FROM visits")
    int howManyElements();

    @Query("SELECT * FROM visits")
    LiveData<List<VisitData>> retrieveVisits();
}
