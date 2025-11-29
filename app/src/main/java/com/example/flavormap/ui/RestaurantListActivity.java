package com.example.flavormap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavormap.R;
import com.example.flavormap.adapters.RestaurantAdapter;
import com.example.flavormap.models.Restaurant;
import com.example.flavormap.storage.RestaurantStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView restaurantRecycler, mostLikedRecycler;
    private LinearLayout cuisineTagContainer;

    private RestaurantAdapter mainAdapter, mostLikedAdapter;

    private List<Restaurant> restaurantList, mostLiked;

    private Button addButton;
    private TextView noResultsText;

    // Exposed so adapter can launch details
    public void openDetailsForResult(Intent intent) {
        detailsLauncher.launch(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Load restaurants from storage
        restaurantList = RestaurantStorage.loadRestaurants(this);

        if (restaurantList.isEmpty()) {
            restaurantList.add(new Restaurant("Sushi Place", "Sushi", "⭐⭐⭐⭐",
                    "123 Eglinton Ave", 4370000000L,
                    "Amazing food", R.drawable.res_6));

            restaurantList.add(new Restaurant("Pasta House", "Italian", "⭐⭐⭐",
                    "456 Uptown Street", 4371112222L,
                    "Delicious pasta", R.drawable.res_6));

            restaurantList.add(new Restaurant("Green Veggies", "Vegan", "⭐⭐⭐⭐⭐",
                    "789 Midtown Blvd", 4373334444L,
                    "Healthy and fresh", R.drawable.res_6));

            RestaurantStorage.saveRestaurants(this, restaurantList);
        }

        // Bind views
        restaurantRecycler = findViewById(R.id.restaurantRecyclerView);
        mostLikedRecycler = findViewById(R.id.mostLikedRecyclerView);
        cuisineTagContainer = findViewById(R.id.cuisineTagContainer);
        addButton = findViewById(R.id.addRestaurantButton);
        noResultsText = findViewById(R.id.noResultsText);
        searchView = findViewById(R.id.restaurantSearchView);

        // Main list (horizontal big cards)
        restaurantRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        mainAdapter = new RestaurantAdapter(this, restaurantList, true, true);
        restaurantRecycler.setAdapter(mainAdapter);

        // --- Add cuisine tags
        addCuisineTag("Sushi");
        addCuisineTag("Pizza");
        addCuisineTag("Vegan");
        addCuisineTag("Ethiopian");
        addCuisineTag("Salads");
        addCuisineTag("Burgers");

        // Top Rated list
        mostLikedRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        mostLiked = new ArrayList<>(restaurantList);
        Collections.sort(mostLiked, (a, b) -> Integer.compare(b.getLikes(), a.getLikes()));
        mostLikedAdapter = new RestaurantAdapter(this, mostLiked, false, false);
        mostLikedRecycler.setAdapter(mostLikedAdapter);

        // Add Restaurant button
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRestaurantActivity.class);
            addRestaurantLauncher.launch(intent);
        });

        // About Us button
        Button aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(v ->
                startActivity(new Intent(this, AboutUsActivity.class)));

        // Search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mainAdapter.filter(query);
                updateEmptyState();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainAdapter.filter(newText);
                updateEmptyState();
                return false;
            }
        });

        updateEmptyState();
    }


    // Cuisine Tag Generator

    private void addCuisineTag(String label) {
        TextView tag = new TextView(this);
        tag.setText(label);
        tag.setTextSize(14);
        tag.setPadding(40, 20, 40, 20);
        tag.setBackground(ContextCompat.getDrawable(this, R.drawable.tag_background));
        tag.setTextColor(Color.parseColor("#333333"));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
        params.setMargins(12, 8, 12, 8);
        tag.setLayoutParams(params);

        // Click → filters main list
        tag.setOnClickListener(v -> {
            mainAdapter.filter(label);
            updateEmptyState();
        });

        cuisineTagContainer.addView(tag);
    }

    private void updateEmptyState() {
        noResultsText.setVisibility(
                mainAdapter.getItemCount() == 0 ? TextView.VISIBLE : TextView.GONE
        );
    }


    // Handle Add Restaurant results

    private final ActivityResultLauncher<Intent> addRestaurantLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

                if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                    Intent data = result.getData();

                    String name = data.getStringExtra("name");
                    String cuisine = data.getStringExtra("cuisine");
                    String rating = data.getStringExtra("rating");
                    String location = data.getStringExtra("address");
                    String description = data.getStringExtra("description");
                    String phoneStr = data.getStringExtra("contact");

                    long phoneLong = 0;
                    try {
                        phoneLong = Long.parseLong(phoneStr);
                    } catch (Exception ignored) {}

                    Restaurant newRestaurant = new Restaurant(
                            name, cuisine, rating, location, phoneLong,
                            description, R.drawable.res_6
                    );

                    restaurantList.add(newRestaurant);
                    RestaurantStorage.saveRestaurants(this, restaurantList);

                    mainAdapter.refreshData(restaurantList);

                    // Update top rated
                    mostLiked.clear();
                    mostLiked.addAll(restaurantList);
                    Collections.sort(mostLiked, (a, b) -> Integer.compare(b.getLikes(), a.getLikes()));
                    mostLikedAdapter.refreshData(mostLiked);

                    updateEmptyState();
                }
            });


    // Handle Edit/Delete

    private final ActivityResultLauncher<Intent> detailsLauncher =
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

                } else if ("edit".equals(action)) {

                    String name = data.getStringExtra("name");
                    String cuisine = data.getStringExtra("cuisine");
                    String rating = data.getStringExtra("rating");
                    String location = data.getStringExtra("address");
                    String description = data.getStringExtra("description");
                    String phoneStr = data.getStringExtra("contact");

                    long phone = 0;
                    try { phone = Long.parseLong(phoneStr); } catch (Exception ignored) {}

                    restaurantList.set(position, new Restaurant(
                            name, cuisine, rating, location, phone,
                            description, R.drawable.res_6
                    ));
                }

                RestaurantStorage.saveRestaurants(this, restaurantList);
                mainAdapter.refreshData(restaurantList);

                mostLiked.clear();
                mostLiked.addAll(restaurantList);
                Collections.sort(mostLiked, (a, b) -> Integer.compare(b.getLikes(), a.getLikes()));
                mostLikedAdapter.refreshData(mostLiked);

                updateEmptyState();
            });
}
