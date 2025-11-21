package com.example.flavormap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flavormap.R;
import com.example.flavormap.models.Restaurant;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.name.setText(restaurant.getName());
        holder.cuisine.setText(restaurant.getCuisine());
        holder.rating.setText(restaurant.getRating());
        holder.image.setImageResource(restaurant.getImageResId());
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, cuisine, rating;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.restaurantImage);
            name = itemView.findViewById(R.id.restaurantName);
            cuisine = itemView.findViewById(R.id.restaurantCuisine);
            rating = itemView.findViewById(R.id.restaurantRating);
        }
    }
}
