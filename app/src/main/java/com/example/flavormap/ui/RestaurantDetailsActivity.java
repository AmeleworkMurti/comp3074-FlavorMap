package com.example.flavormap.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flavormap.R;
import com.example.flavormap.models.Restaurant;

public class RestaurantDetailsActivity extends AppCompatActivity {

    ImageView detailImage, editRestaurant, deleteRestaurant;
    TextView detailName, detailTags, detailLocation, detailPhone, detailRating, detailDescription;
    Button viewMapBtn, directionsBtn, shareBtn;

    Restaurant currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // Receive restaurant object correctly
        currentRestaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

        if (currentRestaurant == null) {
            finish();
            return;
        }

        // Bind views
        detailImage = findViewById(R.id.detailImage);
        editRestaurant = findViewById(R.id.editRestaurant);
        deleteRestaurant = findViewById(R.id.deleteRestaurant);
        detailName = findViewById(R.id.detailName);
        detailTags = findViewById(R.id.detailTags);
        detailLocation = findViewById(R.id.detailLocation);
        detailPhone = findViewById(R.id.detailPhone);
        detailRating = findViewById(R.id.detailRating);
        detailDescription = findViewById(R.id.detailDescription);

        viewMapBtn = findViewById(R.id.viewMapBtn);
        directionsBtn = findViewById(R.id.directionsBtn);
        shareBtn = findViewById(R.id.shareBtn);

        // Display restaurant details
        detailName.setText(currentRestaurant.getName());
        detailTags.setText("Tags: " + currentRestaurant.getCuisine());
        detailLocation.setText("Location: " + currentRestaurant.getLocation());
        detailPhone.setText("Phone: " + currentRestaurant.getPhone());
        detailRating.setText("⭐ Rating: " + currentRestaurant.getRating());
        detailDescription.setText(currentRestaurant.getDescription());
        detailImage.setImageResource(currentRestaurant.getImageResId());

        // Directions button
        directionsBtn.setOnClickListener(v -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(currentRestaurant.getLocation()));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        });

        //View Map
        viewMapBtn.setOnClickListener(v -> {
            String location = currentRestaurant.getLocation(); // e.g. "123 Eglinton Ave, Toronto"
            String name = currentRestaurant.getName();

            // Open Google Maps search with a pin
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(location + " (" + name + ")"));

            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                // fallback: open in browser if Google Maps not installed
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(location)));
                startActivity(browserIntent);
            }
        });





        // Share button
        Button shareBtn = findViewById(R.id.shareBtn); // make sure the ID matches XML
        shareBtn.setOnClickListener(v -> {
            ShareDialogFragment dialog = new ShareDialogFragment();
            dialog.show(getSupportFragmentManager(), "share_dialog");
        });

    }
}
