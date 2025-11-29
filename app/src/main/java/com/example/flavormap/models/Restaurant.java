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
    private int likes;
    // Needed for Gson / Serialization
    public Restaurant(){

    }
    public Restaurant(String name, String cuisine, String rating, String location,
                      long phone, String description, int imageResId) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.phone = phone;
        this.description = description;
        this.imageResId = imageResId;
        this.likes=0;

    }
    public Restaurant(String name, String cuisine, String rating, String location,
                      long phone, String description, int imageResId, int likes) {
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.phone = phone;
        this.description = description;
        this.imageResId = imageResId;
        this.likes = likes;
    }


    public String getName() { return name; }
    public String getCuisine() { return cuisine; }
    public String getRating() { return rating; }
    public String getLocation() { return location; }
    public long getPhone() { return phone; }
    public String getDescription() { return description; }
    public int getImageResId() { return imageResId; }
    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

}
