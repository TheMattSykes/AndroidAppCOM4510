/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

import myapplication.uk.ac.shef.oak.myapplication.model.VisitData;

@Dao
public interface VisitDao {
    @Query("SELECT * FROM visits")
    LiveData<Image> getAll();

    @Insert
    void insertAll(List<VisitData> visit);

    @Insert
    void insert(Visit visit);

    @Delete
    void delete(Visit visit);

    @Delete
    void deleteAll(List<VisitData> visit);
}
