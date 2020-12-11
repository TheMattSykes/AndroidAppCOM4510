/*
 * Copyright (c) 2020. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package myapplication.uk.ac.shef.oak.myapplication;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "images")
public class Image {
    @PrimaryKey(autoGenerate = true)
    @androidx.annotation.NonNull
    public int uid;

    @ColumnInfo(name = "picture", typeAffinity = ColumnInfo.BLOB)
    public byte[] picture;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "geolocation")
    public String geolocation;
}
