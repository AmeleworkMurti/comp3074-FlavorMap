package com.example.flavormap.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class RestaurantEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String cuisine;
    public String rating;
    public String location;
    public long phone;
    public String description;

    // Image (URI string). If empty → use default drawable.
    public String imageUri = "";

    public int likes = 0;
}
