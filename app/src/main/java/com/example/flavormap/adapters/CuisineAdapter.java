package com.example.flavormap.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flavormap.R;
import com.example.flavormap.models.Cuisine;

import java.util.List;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder> {

    private List<Cuisine> cuisineList;

    public CuisineAdapter(List<Cuisine> cuisineList) { this.cuisineList = cuisineList; }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cuisine, parent, false);
        return new CuisineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuisineViewHolder holder, int position) {
        Cuisine cuisine = cuisineList.get(position);
        holder.cuisineName.setText(cuisine.getName());
    }

    @Override
    public int getItemCount() { return cuisineList.size(); }

    static class CuisineViewHolder extends RecyclerView.ViewHolder {
        TextView cuisineName;
        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);
            cuisineName = itemView.findViewById(R.id.cuisineName);
        }
    }
}

