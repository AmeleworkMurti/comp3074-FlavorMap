package com.example.flavormap.db;

import android.content.Context;

import com.example.flavormap.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {

    private final RestaurantDao restaurantDao;

    public RestaurantRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        restaurantDao = db.restaurantDao();
    }

    // Get all restaurants as UI model
    public List<Restaurant> getAllRestaurants() {
        List<RestaurantEntity> entities = restaurantDao.getAll();
        List<Restaurant> result = new ArrayList<>();
        for (RestaurantEntity e : entities) {
            result.add(toModel(e));
        }
        return result;
    }

    // Insert and update model id with generated primary key
    public long insertRestaurant(Restaurant restaurant) {
        RestaurantEntity entity = toEntity(restaurant);
        long id = restaurantDao.insert(entity);
        restaurant.setId((int) id);
        return id;
    }

    public void updateRestaurant(Restaurant restaurant) {
        RestaurantEntity entity = toEntity(restaurant);
        restaurantDao.update(entity);
    }

    public void deleteRestaurant(Restaurant restaurant) {
        RestaurantEntity entity = toEntity(restaurant);
        restaurantDao.delete(entity);
    }

    //  Mapping helpers

    private static Restaurant toModel(RestaurantEntity e) {
        return new Restaurant(
                e.id,
                e.name,
                e.cuisine,
                e.rating,
                e.location,
                e.phone,
                e.description,
                e.imageUri,
                e.likes
        );
    }

    private static RestaurantEntity toEntity(Restaurant r) {
        RestaurantEntity e = new RestaurantEntity();
        e.id = r.getId();
        e.name = r.getName();
        e.cuisine = r.getCuisine();
        e.rating = r.getRating();
        e.location = r.getLocation();
        e.phone = r.getPhone();
        e.description = r.getDescription();
        e.imageUri = r.getImageUri();
        e.likes = r.getLikes();
        return e;
    }
}
