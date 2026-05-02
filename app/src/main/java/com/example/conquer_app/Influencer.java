package com.example.conquer_app;

public class Influencer {
    private String name, handle, location, category, followers;

    public Influencer(String name, String handle, String location, String category, String followers) {
        this.name = name;
        this.handle = handle;
        this.location = location;
        this.category = category;
        this.followers = followers;
    }

    public String getName() { return name; }
    public String getHandle() { return handle; }
    public String getLocation() { return location; }
    public String getCategory() { return category; }
    public String getFollowers() { return followers; }
