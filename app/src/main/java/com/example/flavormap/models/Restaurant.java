package com.example.flavormap.models;

public class Restaurant {
    private String name;
    private String cuisine;
    private String rating;
    private String location;  // new field
    private int imageResId;

    public Restaurant(String name, String cuisine, String rating, String location, int imageResId) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public String getRating() { return rating; }
    public String getLocation() { return location; }
    public int getImageResId() { return imageResId; }
}
