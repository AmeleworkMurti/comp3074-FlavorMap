package com.example.flavormap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flavormap.R;
import com.example.flavormap.adapters.CuisineAdapter;
import com.example.flavormap.adapters.RestaurantAdapter;
import com.example.flavormap.models.Cuisine;
import com.example.flavormap.models.Restaurant;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView, cuisineRecycler, mostLikedRecycler;
    private RestaurantAdapter adapter, mostLikedAdapter;
    private CuisineAdapter cuisineAdapter;
    private List<Restaurant> restaurantList, mostLiked;
    private List<Cuisine> cuisines;
    private FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // --- Main restaurant grid ---
        recyclerView = findViewById(R.id.restaurantRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Dummy data for main restaurants
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("Sushi Place", "Sushi", "⭐⭐⭐⭐", "Downtown", R.drawable.ic_launcher_background));
        restaurantList.add(new Restaurant("Pasta House", "Italian", "⭐⭐⭐", "Uptown", R.drawable.ic_launcher_background));
        restaurantList.add(new Restaurant("Green Veggies", "Vegan", "⭐⭐⭐⭐⭐", "Midtown", R.drawable.ic_launcher_background));

        adapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(adapter);

        // --- SearchView setup ---
        searchView = findViewById(R.id.restaurantSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterRestaurants(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterRestaurants(newText);
                return false;
            }
        });

        // --- Browse by Cuisine (Horizontal RecyclerView) ---
        cuisineRecycler = findViewById(R.id.cuisineRecyclerView);
        cuisineRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        cuisines = new ArrayList<>();
        cuisines.add(new Cuisine("Sushi"));
        cuisines.add(new Cuisine("Pizza"));
        cuisines.add(new Cuisine("Vegan"));
        cuisineAdapter = new CuisineAdapter(cuisines);
        cuisineRecycler.setAdapter(cuisineAdapter);

        // --- Most Liked Food (Horizontal RecyclerView) ---
        mostLikedRecycler = findViewById(R.id.mostLikedRecyclerView);
        mostLikedRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mostLiked = new ArrayList<>();
        mostLiked.add(new Restaurant("Sushi Place", "Sushi", "⭐⭐⭐⭐", "Downtown", R.drawable.ic_launcher_background));
        mostLiked.add(new Restaurant("Green Veggies", "Vegan", "⭐⭐⭐⭐⭐", "Midtown", R.drawable.ic_launcher_background));
        mostLikedAdapter = new RestaurantAdapter(mostLiked);
        mostLikedRecycler.setAdapter(mostLikedAdapter);

        // --- FloatingActionButton to Add Restaurant ---
        addButton = findViewById(R.id.addRestaurantButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantListActivity.this, AddRestaurantActivity.class);
            startActivity(intent);
        });
    }

    // Filter method outside onCreate
    private void filterRestaurants(String text) {
        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getName().toLowerCase().contains(text.toLowerCase()) ||
                    r.getCuisine().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(r);
            }
        }
        adapter = new RestaurantAdapter(filteredList);
        recyclerView.setAdapter(adapter);
    }
}
