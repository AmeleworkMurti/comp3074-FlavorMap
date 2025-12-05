package com.example.flavormap.models;

import java.io.Serializable;

public class Restaurant implements Serializable {

    private int id;
    private String name;
    private String cuisine;
    private String rating;
    private String location;
    private String description;
    private long phone;
    private String imageUri;   // content:// or file://; empty = default image
    private int likes;

    public Restaurant() {
    }

    public Restaurant(int id,
                      String name,
                      String cuisine,
                      String rating,
                      String location,
                      long phone,
                      String description,
                      String imageUri,
                      int likes) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.location = location;
        this.phone = phone;
        this.description = description;
        this.imageUri = imageUri;
        this.likes = likes;
    }

    // Convenience constructor when creating a brand new restaurant in UI
    public Restaurant(String name,
                      String cuisine,
                      String rating,
                      String location,
                      long phone,
                      String description,
                      String imageUri) {
        this(0, name, cuisine, rating, location, phone, description, imageUri, 0);
    }

    //  Getters & Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCuisine() { return cuisine; }
    public void setCuisine(String cuisine) { this.cuisine = cuisine; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getPhone() { return phone; }
    public void setPhone(long phone) { this.phone = phone; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }
}
