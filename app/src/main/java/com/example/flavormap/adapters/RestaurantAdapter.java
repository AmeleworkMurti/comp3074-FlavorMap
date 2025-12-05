package com.example.flavormap.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavormap.R;
import com.example.flavormap.models.Restaurant;
import com.example.flavormap.ui.RestaurantDetailsActivity;
import com.example.flavormap.ui.RestaurantListActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private final Context context;
    private List<Restaurant> originalList;   // master list
    private List<Restaurant> displayList;    // filtered list
    private final boolean enableDetails;
    private final boolean enableLikes;

    // BIG (main section) vs SMALL (top rated)
    private final boolean useBigCard;

    public RestaurantAdapter(Context context,
                             List<Restaurant> restaurantList,
                             boolean enableDetails,
                             boolean enableLikes) {

        this.context = context;
        this.originalList = restaurantList;
        this.displayList = new ArrayList<>(restaurantList);
        this.enableDetails = enableDetails;
        this.enableLikes = enableLikes;

        // Main list uses big cards; top-rated uses small cards
        this.useBigCard = enableDetails;
    }

    @Override
    public int getItemViewType(int position) {
        return useBigCard ? 1 : 2;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        int layoutId = (viewType == 1)
                ? R.layout.item_restaurant_big
                : R.layout.item_restaurant_small;

        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = displayList.get(position);

        holder.name.setText(restaurant.getName());
        holder.cuisine.setText(restaurant.getCuisine());
        holder.rating.setText(restaurant.getRating());
        holder.location.setText(restaurant.getLocation());
        holder.likesCount.setText(String.valueOf(restaurant.getLikes()));

        // Load image: user-selected if available, otherwise default drawable
        String imageUri = restaurant.getImageUri();
        if (imageUri != null && !imageUri.isEmpty()) {
            holder.image.setImageURI(Uri.parse(imageUri));
        } else {
            holder.image.setImageResource(R.drawable.res_6); // our default card image
        }

        // DETAILS click – only for main list
        if (enableDetails && context instanceof RestaurantListActivity) {
            holder.itemView.setOnClickListener(v -> {
                int originalIndex = originalList.indexOf(restaurant);
                if (originalIndex < 0) return;

                Intent intent = new Intent(context, RestaurantDetailsActivity.class);
                intent.putExtra("restaurant", restaurant);
                intent.putExtra("position", originalIndex);

                ((RestaurantListActivity) context).openDetailsForResult(intent);
            });
        } else {
            holder.itemView.setOnClickListener(null);
        }

        // LIKE icon – only for main list (just updates in-memory for now)
        if (enableLikes) {
            holder.likeIcon.setOnClickListener(v -> {
                restaurant.setLikes(restaurant.getLikes() + 1);
                holder.likesCount.setText(String.valueOf(restaurant.getLikes()));

            });
        } else {
            holder.likeIcon.setOnClickListener(null);
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    // Called when main list changes (add/edit/delete)
    public void refreshData(List<Restaurant> newList) {
        this.originalList = newList;
        this.displayList = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    // Search filter (name or cuisine)
    public void filter(String query) {
        displayList.clear();

        if (query == null || query.trim().isEmpty()) {
            displayList.addAll(originalList);
        } else {
            String lower = query.toLowerCase();
            for (Restaurant r : originalList) {
                if (r.getName().toLowerCase().contains(lower)
                        || r.getCuisine().toLowerCase().contains(lower)) {
                    displayList.add(r);
                }
            }
        }
        notifyDataSetChanged();
    }

    // Filter by cuisine (from chips)
    public void filterByCuisine(String cuisine) {
        displayList.clear();

        if (cuisine == null || cuisine.equalsIgnoreCase("All")) {
            displayList.addAll(originalList);
        } else {
            for (Restaurant r : originalList) {
                if (r.getCuisine().equalsIgnoreCase(cuisine)) {
                    displayList.add(r);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView name, cuisine, rating, location, likesCount;
        ImageView image, likeIcon;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.restaurantName);
            cuisine = itemView.findViewById(R.id.restaurantCuisine);
            rating = itemView.findViewById(R.id.restaurantRating);
            location = itemView.findViewById(R.id.restaurantLocation);
            image = itemView.findViewById(R.id.restaurantImage);
            likeIcon = itemView.findViewById(R.id.likeIcon);
            likesCount = itemView.findViewById(R.id.likesCount);
        }
    }
}
