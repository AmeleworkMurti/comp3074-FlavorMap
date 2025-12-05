package com.example.flavormap.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flavormap.R;
import com.example.flavormap.models.Restaurant;

public class RestaurantDetailsActivity extends AppCompatActivity {

    ImageView detailImage, editRestaurant, deleteRestaurant;
    TextView detailName, detailTags, detailLocation, detailPhone, detailRating, detailDescription;
    Button viewMapBtn, directionsBtn, shareBtn;

    Restaurant currentRestaurant;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        position = getIntent().getIntExtra("position", -1);

        // Back arrow & title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Restaurant Details");
        }

        // Get restaurant object from intent
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

        ImageView backButton = findViewById(R.id.backButtonDetails);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }

        // Fill UI with data
        detailName.setText(currentRestaurant.getName());
        detailTags.setText("Tags: " + currentRestaurant.getCuisine());
        detailLocation.setText("Location: " + currentRestaurant.getLocation());
        detailPhone.setText("Phone: " + currentRestaurant.getPhone());
        detailRating.setText("⭐ Rating: " + currentRestaurant.getRating());
        detailDescription.setText(currentRestaurant.getDescription());
        String imgUri = currentRestaurant.getImageUri();

        if (imgUri != null && !imgUri.isEmpty()) {
            detailImage.setImageURI(Uri.parse(imgUri));
        } else {
            detailImage.setImageResource(R.drawable.res_6); // fallback image
        }


        // Directions -> navigation from current location
        directionsBtn.setOnClickListener(v -> {
            String location = currentRestaurant.getLocation();

            Uri navUri = Uri.parse("google.navigation:q=" + Uri.encode(location));
            Intent navIntent = new Intent(Intent.ACTION_VIEW, navUri);
            navIntent.setPackage("com.google.android.apps.maps");

            if (navIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(navIntent);
            } else {
                Uri browserUri = Uri.parse(
                        "https://www.google.com/maps/dir/?api=1&destination=" + Uri.encode(location)
                );
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
                startActivity(browserIntent);
            }
        });

        // View Map -> just show place on the map
        viewMapBtn.setOnClickListener(v -> {
            String location = currentRestaurant.getLocation();
            String name = currentRestaurant.getName();

            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(location + " (" + name + ")"));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Intent browserIntent = new Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(location))
                );
                startActivity(browserIntent);
            }
        });

        // Share button -> open share dialog
        shareBtn.setOnClickListener(v -> {
            String ratingText = currentRestaurant.getRating();
            ShareDialogFragment dialog = ShareDialogFragment.newInstance(
                    currentRestaurant.getName(),
                    currentRestaurant.getLocation(),
                    ratingText
            );
            dialog.show(getSupportFragmentManager(), "share_dialog");
        });

        // DELETE icon -> confirmation dialog
        deleteRestaurant.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Delete restaurant")
                    .setMessage("Are you sure you want to delete this restaurant?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        Intent result = new Intent();
                        result.putExtra("action", "delete");
                        result.putExtra("position", position);
                        setResult(RESULT_OK, result);
                        finish();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // EDIT icon -> open AddRestaurantActivity with pre-filled info
        editRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantDetailsActivity.this, AddRestaurantActivity.class);
            intent.putExtra("name", currentRestaurant.getName());
            intent.putExtra("location", currentRestaurant.getLocation());
            intent.putExtra("cuisine", currentRestaurant.getCuisine());
            intent.putExtra("rating", currentRestaurant.getRating());
            intent.putExtra("description", currentRestaurant.getDescription());
            intent.putExtra("phone", String.valueOf(currentRestaurant.getPhone()));
            // Launch AddRestaurantActivity for result
            editLauncher.launch(intent);
        });
    }
    private final ActivityResultLauncher<Intent> editLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    data.putExtra("action", "edit");
                    data.putExtra("position", position);
                    setResult(RESULT_OK, data);
                    finish();
                }
            });


    // Back arrow behavior (top-left arrow)
    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }
}
