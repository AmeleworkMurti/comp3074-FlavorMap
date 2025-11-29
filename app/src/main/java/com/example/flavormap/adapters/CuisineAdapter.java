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

    public interface OnCuisineClickListener {
        void onCuisineClick(String cuisineName);
    }

    private final List<Cuisine> cuisines;
    private final OnCuisineClickListener listener;
    private int selectedPosition = -1;

    public CuisineAdapter(List<Cuisine> cuisines, OnCuisineClickListener listener) {
        this.cuisines = cuisines;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cuisine_chip, parent, false);
        return new CuisineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CuisineViewHolder holder, int position) {
        Cuisine cuisine = cuisines.get(position);
        holder.name.setText(cuisine.getName());

        // Apply selected/unselected style
        holder.name.setTypeface(
                null,
                position == selectedPosition
                        ? android.graphics.Typeface.BOLD
                        : android.graphics.Typeface.NORMAL
        );

        holder.itemView.setOnClickListener(v -> {
            int adapterPos = holder.getAdapterPosition();
            if (adapterPos == RecyclerView.NO_POSITION) return;

            int oldPos = selectedPosition;

            // Toggle selection
            if (selectedPosition == adapterPos) {
                selectedPosition = -1;
                listener.onCuisineClick("All");
            } else {
                selectedPosition = adapterPos;
                listener.onCuisineClick(cuisines.get(adapterPos).getName());
            }

            // Update UI
            if (oldPos >= 0) notifyItemChanged(oldPos);
            notifyItemChanged(adapterPos);
        });
    }

    @Override
    public int getItemCount() {
        return cuisines.size();
    }

    static class CuisineViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cuisineName);
        }
    }
}
