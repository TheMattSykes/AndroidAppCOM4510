/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "visits")
public class Visit {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    public int id;

    @ColumnInfo(name = "name")
    public String name;
}
