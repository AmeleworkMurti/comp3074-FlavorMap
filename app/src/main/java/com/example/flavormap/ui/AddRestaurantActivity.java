package com.example.flavormap.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flavormap.R;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText restaurantName, address,
            cuisineType, rating, description, contactInfo;
    private Button addRestaurantBtn;

    private ImageView restaurantImagePreview;
    private String selectedImageUri = "";

    private final ActivityResultLauncher<String> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    selectedImageUri = uri.toString();
                    restaurantImagePreview.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        // Back button
        ImageView backBtn = findViewById(R.id.backButtonDetails);
        backBtn.setOnClickListener(v ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        // Image preview view
        restaurantImagePreview = findViewById(R.id.restaurantImagePreview);

        // Click to open gallery
        restaurantImagePreview.setOnClickListener(v ->
                imagePickerLauncher.launch("image/*")
        );

        // Toolbar title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Restaurant");
        }

        // Bind input fields
        restaurantName = findViewById(R.id.restaurantName);
        address = findViewById(R.id.address);
        cuisineType = findViewById(R.id.cuisineType);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.description);
        contactInfo = findViewById(R.id.contactInfo);
        addRestaurantBtn = findViewById(R.id.addRestaurantBtn);

        // Prefill if editing
        Intent fromDetails = getIntent();
        if (fromDetails != null && fromDetails.hasExtra("name")) {
            restaurantName.setText(fromDetails.getStringExtra("name"));
            address.setText(fromDetails.getStringExtra("location"));
            cuisineType.setText(fromDetails.getStringExtra("cuisine"));
            rating.setText(fromDetails.getStringExtra("rating"));
            description.setText(fromDetails.getStringExtra("description"));
            contactInfo.setText(fromDetails.getStringExtra("phone"));
        }

        // Handle Add button
        addRestaurantBtn.setOnClickListener(v -> {

            String name = restaurantName.getText().toString();
            String addr = address.getText().toString();
            String cuisine = cuisineType.getText().toString();
            String rate = rating.getText().toString();
            String desc = description.getText().toString();
            String contact = contactInfo.getText().toString();

            if (name.isEmpty() || addr.isEmpty() || cuisine.isEmpty()) {
                Toast.makeText(this, "Please fill in required fields", Toast.LENGTH_LONG).show();
                return;
            }

            // Format rating
            if (!rate.isEmpty() && !rate.contains("★") && !rate.startsWith("⭐")) {
                rate = rate + "★";
            }

            // Return data to RestaurantListActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("name", name);
            resultIntent.putExtra("address", addr);
            resultIntent.putExtra("cuisine", cuisine);
            resultIntent.putExtra("rating", rate);
            resultIntent.putExtra("description", desc);
            resultIntent.putExtra("contact", contact);
            resultIntent.putExtra("imageUri", selectedImageUri);

            setResult(RESULT_OK, resultIntent);
            finish();

            Toast.makeText(this, name + " added!", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
