package com.fooddelivery.model;

public class Cuisine {
    private static long nextId = 100;
    private String cuisineId;
    private String cuisineName;

    public Cuisine(String cuisineId, String cuisineName) {
        this.cuisineId = "CUISINE-" + (++nextId);
        this.cuisineName = cuisineName;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }
}
