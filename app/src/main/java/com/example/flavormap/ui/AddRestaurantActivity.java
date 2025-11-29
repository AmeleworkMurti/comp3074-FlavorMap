package com.example.flavormap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flavormap.R;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText restaurantName, address,
            cuisineType, rating, description, contactInfo;
    private Button addRestaurantBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        ImageView backBtn = findViewById(R.id.backButtonDetails);
        backBtn.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Restaurant");
        }

        // Bind views
        restaurantName = findViewById(R.id.restaurantName);
        address = findViewById(R.id.address);
        cuisineType = findViewById(R.id.cuisineType);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.description);
        contactInfo = findViewById(R.id.contactInfo);
        addRestaurantBtn = findViewById(R.id.addRestaurantBtn);

        // If opened from "Edit", prefill fields
        Intent fromDetails = getIntent();
        if (fromDetails != null && fromDetails.hasExtra("name")) {
            restaurantName.setText(fromDetails.getStringExtra("name"));
            address.setText(fromDetails.getStringExtra("location"));
            cuisineType.setText(fromDetails.getStringExtra("cuisine"));
            rating.setText(fromDetails.getStringExtra("rating"));
            description.setText(fromDetails.getStringExtra("description"));
            contactInfo.setText(fromDetails.getStringExtra("phone"));
        }

        // Handle Add button click
        addRestaurantBtn.setOnClickListener(v -> {
            String name = restaurantName.getText().toString();
            String addr = address.getText().toString();
            String cuisine = cuisineType.getText().toString();
            String rate = rating.getText().toString();
            String desc = description.getText().toString();
            String contact = contactInfo.getText().toString();

            if(name.isEmpty() || addr.isEmpty() || cuisine.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_LONG).show();
                return;
            }
            // Rating as "4★" (if user only types 4)
            if (!rate.isEmpty() && !rate.contains("★") && !rate.startsWith("⭐")) {
                rate = rate + "★";
            }

            // Create intent to send back
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("address", addr);
            resultIntent.putExtra("cuisine", cuisine);
            resultIntent.putExtra("rating", rate);
            resultIntent.putExtra("description", desc);
            resultIntent.putExtra("contact", contact);
            setResult(RESULT_OK, resultIntent);
            finish(); // closes AddRestaurant and goes back to MainActivity



            // todo: Save restaurant to my list, adapter, or database
            Toast.makeText(this, name + " added!", Toast.LENGTH_LONG).show();

            // Optional: clear fields
            restaurantName.setText("");
            address.setText("");
            cuisineType.setText("");
            rating.setText("");
            description.setText("");
            contactInfo.setText("");
        });
    }
    // Back arrow behavior
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}

