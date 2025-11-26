package com.example.flavormap.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.flavormap.models.Restaurant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RestaurantStorage {

    private static final String PREF_NAME = "restaurant_prefs";
    private static final String KEY_RESTAURANTS = "restaurants";

    public static void saveRestaurants(Context context, List<Restaurant> list) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(KEY_RESTAURANTS, json);
        editor.apply(); // Async write
    }

    public static List<Restaurant> loadRestaurants(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_RESTAURANTS, null);

        if (json == null) {
            return new ArrayList<>(); // empty list default
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Restaurant>>() {}.getType();

        return gson.fromJson(json, type);
    }
}
