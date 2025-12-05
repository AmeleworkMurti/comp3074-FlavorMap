package com.example.flavormap.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Query("SELECT * FROM restaurants")
    List<RestaurantEntity> getAll();

    @Insert
    long insert(RestaurantEntity restaurant);

    @Update
    void update(RestaurantEntity restaurant);

    @Delete
    void delete(RestaurantEntity restaurant);
}
