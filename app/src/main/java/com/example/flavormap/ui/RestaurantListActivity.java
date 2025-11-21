package com.example.flavormap.ui;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flavormap.R;
import com.example.flavormap.adapters.RestaurantAdapter;
import com.example.flavormap.models.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    private SearchView searchView;

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        recyclerView = findViewById(R.id.restaurantRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 🔹 Dummy data
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("Sushi Place", "Sushi", "⭐⭐⭐⭐", R.drawable.ic_launcher_background));
        restaurantList.add(new Restaurant("Pasta House", "Italian", "⭐⭐⭐", R.drawable.ic_launcher_background));
        restaurantList.add(new Restaurant("Green Veggies", "Vegan", "⭐⭐⭐⭐⭐", R.drawable.ic_launcher_background));

        adapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(adapter);

        // search
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
    }

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

