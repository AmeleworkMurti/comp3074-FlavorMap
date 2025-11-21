package com.example.flavormap.models;

public class Restaurant {
    private String name;
    private String cuisine;
    private String rating;
    private int imageResId;

    public Restaurant(String name, String cuisine, String rating, int imageResId) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public String getRating() { return rating; }
    public int getImageResId() { return imageResId; }
}
