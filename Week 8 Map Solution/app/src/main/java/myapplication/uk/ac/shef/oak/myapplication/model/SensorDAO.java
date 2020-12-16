package myapplication.uk.ac.shef.oak.myapplication.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SensorDAO {
    @Insert
    void insertAll(SensorData... sensorData);

    @Insert
    void insert(SensorData sensorData);

    @Delete
    void delete(SensorData sensorData);

    @Delete
    void deleteAll(SensorData... sensorData);

    @Query("SELECT COUNT(*) FROM sensors")
    int howManyElements();

    @Query("SELECT * FROM sensors")
    LiveData<List<SensorData>> retrieveSensorData();

    @Query("SELECT * FROM sensors WHERE visitId = :visitId")
    LiveData<List<SensorData>> retrieveVisitSensorData(int visitId);
}
