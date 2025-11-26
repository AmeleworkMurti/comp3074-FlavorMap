package com.example.flavormap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavormap.R;
import com.example.flavormap.adapters.CuisineAdapter;
import com.example.flavormap.adapters.RestaurantAdapter;
import com.example.flavormap.models.Cuisine;
import com.example.flavormap.models.Restaurant;
import com.example.flavormap.storage.RestaurantStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private static final int ADD_RESTAURANT_REQUEST = 100;

    private SearchView searchView;
    private RecyclerView recyclerView, cuisineRecycler, mostLikedRecycler;
    private RestaurantAdapter adapter, mostLikedAdapter;
    private CuisineAdapter cuisineAdapter;
    private List<Restaurant> restaurantList, mostLiked;
    private List<Cuisine> cuisines;
    private FloatingActionButton addButton;
    private static final int DETAILS_REQUEST = 200;

    public void openDetailsForResult(Intent intent) {
        detailsLauncher.launch(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        restaurantList = RestaurantStorage.loadRestaurants(this);

        if (restaurantList.isEmpty()) {
            // Add demo restaurants ONLY first time
            restaurantList.add(new Restaurant("Sushi Place","Sushi","⭐⭐⭐⭐","123 Eglinton Ave",4370000000L,"Amazing food",R.drawable.ic_launcher_background));
            restaurantList.add(new Restaurant("Pasta House","Italian","⭐⭐⭐","456 Uptown Street",4371112222L,"Delicious pasta",R.drawable.ic_launcher_background));
            restaurantList.add(new Restaurant("Green Veggies","Vegan","⭐⭐⭐⭐⭐","789 Midtown Blvd",4373334444L,"Healthy and fresh",R.drawable.ic_launcher_background));
        }


        // --- Main restaurant grid ---
        recyclerView = findViewById(R.id.restaurantRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        adapter = new RestaurantAdapter(this, restaurantList);

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
        mostLiked.add(new Restaurant(
                "Sushi Place",          // name
                "Sushi",                // cuisine
                "⭐⭐⭐⭐",                 // rating
                "123 Downtown St",      // location
                4370000000L,            // phone (long)
                "Amazing food",         // description
                R.drawable.ic_launcher_background // image
        ));
        mostLiked.add(new Restaurant(
                "Green Veggies",
                "Vegan",
                "⭐⭐⭐⭐⭐",
                "789 Midtown Blvd",
                4373334444L,
                "Healthy and fresh",
                R.drawable.ic_launcher_background
        ));

        mostLikedAdapter = new RestaurantAdapter(this, mostLiked);
        mostLikedRecycler.setAdapter(mostLikedAdapter);


        // --- FloatingActionButton to Add Restaurant ---
        addButton = findViewById(R.id.addRestaurantButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantListActivity.this, AddRestaurantActivity.class);
            addRestaurantLauncher.launch(intent);

        });

        // Wiring the button
        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantListActivity.this, AboutUsActivity.class);
            startActivity(intent);
        });

    }
    private ActivityResultLauncher<Intent> addRestaurantLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Intent data = result.getData();

                    String name = data.getStringExtra("name");
                    String cuisine = data.getStringExtra("cuisine");
                    String rating = data.getStringExtra("rating");
                    String location = data.getStringExtra("address");     // FIXED KEY
                    String description = data.getStringExtra("description");
                    String phoneStr = data.getStringExtra("contact");      // FIXED KEY

                    long phoneLong = 0;
                    try {
                        phoneLong = Long.parseLong(phoneStr);
                    } catch (Exception ignored) {}

                    Restaurant newRestaurant = new Restaurant(
                            name,
                            cuisine,
                            rating,
                            location,
                            phoneLong,
                            description,
                            R.drawable.ic_launcher_background
                    );

                    restaurantList.add(newRestaurant);
                    adapter.notifyItemInserted(restaurantList.size() - 1);
                    RestaurantStorage.saveRestaurants(this, restaurantList);

                }
            });
    private ActivityResultLauncher<Intent> detailsLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

                if (result.getResultCode() != RESULT_OK || result.getData() == null)
                    return;

                Intent data = result.getData();
                String action = data.getStringExtra("action");
                int position = data.getIntExtra("position", -1);

                if (position < 0 || position >= restaurantList.size())
                    return;

                if ("delete".equals(action)) {
                    restaurantList.remove(position);
                    adapter.notifyItemRemoved(position);
                    RestaurantStorage.saveRestaurants(this, restaurantList);
                }
                else if ("edit".equals(action)) {

                    String name = data.getStringExtra("name");
                    String cuisine = data.getStringExtra("cuisine");
                    String rating = data.getStringExtra("rating");
                    String location = data.getStringExtra("address");
                    String description = data.getStringExtra("description");
                    String phoneStr = data.getStringExtra("contact");

                    long phone = 0;
                    try { phone = Long.parseLong(phoneStr); } catch (Exception ignored) {}

                    Restaurant updated = new Restaurant(
                            name, cuisine, rating, location, phone, description,
                            R.drawable.ic_launcher_background
                    );

                    restaurantList.set(position, updated);
                    adapter.notifyItemChanged(position);
                    RestaurantStorage.saveRestaurants(this, restaurantList);
                }
            });

    // Filter method outside onCreate
    private void filterRestaurants(String text) {
        List<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant r : restaurantList) {
            if (r.getName().toLowerCase().contains(text.toLowerCase()) ||
                    r.getCuisine().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(r);
            }
        }
        adapter.updateList(filteredList);
        /*adapter = new RestaurantAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);*/
    }
}
