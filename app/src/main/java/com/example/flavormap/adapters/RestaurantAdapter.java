package com.example.flavormap.adapters;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Restaurant> restaurantList;


    public RestaurantAdapter(Context context, List<Restaurant> restaurantList) {
        this.context = context;
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
        holder.location.setText(restaurant.getLocation());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetailsActivity.class);
            intent.putExtra("restaurant", restaurant);
            intent.putExtra("position", position);

            // Must go through RestaurantListActivity to get results
            if (context instanceof RestaurantListActivity) {
                ((RestaurantListActivity) context).openDetailsForResult(intent);
            }
        });


   /*     // CLICK LISTENER MUST BE INSIDE onBindViewHolder
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetailsActivity.class);
            intent.putExtra("name", restaurant.getName());
            intent.putExtra("cuisine", restaurant.getCuisine());
            intent.putExtra("location", restaurant.getLocation());
            intent.putExtra("phone", restaurant.getPhone());
            intent.putExtra("rating", restaurant.getRating());
            intent.putExtra("image", restaurant.getImageResId());
            intent.putExtra("description", restaurant.getDescription());
            intent.putExtra("restaurant", restaurantList.get(position));
            context.startActivity(intent);
        });*/
    }


    public void updateList(List<Restaurant> newList) {
        this.restaurantList = newList;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView name, cuisine, rating, location;
        ImageView image;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.restaurantName);
            cuisine = itemView.findViewById(R.id.restaurantCuisine);
            rating = itemView.findViewById(R.id.restaurantRating);
            location = itemView.findViewById(R.id.restaurantLocation);
            image = itemView.findViewById(R.id.restaurantImage);
        }
    }

}
