package com.example.flavormap.models;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String name;
    private String cuisine;
    private String rating;
    private String location;
    private String description;
    private long phone;
    private int imageResId;

    public Restaurant(String name, String cuisine, String rating, String location,
                      long phone, String description, int imageResId) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.phone = phone;
        this.description = description;
        this.imageResId = imageResId;

    }

    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public String getRating() { return rating; }
    public String getLocation() { return location; }
    public long getPhone() { return phone; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }

}
