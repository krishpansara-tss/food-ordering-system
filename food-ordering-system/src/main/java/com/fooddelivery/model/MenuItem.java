package com.fooddelivery.model;

import com.fooddelivery.enums.CuisineType;

public class MenuItem {
    private static long itemCount = 1000;
    private String menuItemId;
    private String itemName;
    private double price;
    private boolean isVeg;
    private CuisineType cuisineType;

    public MenuItem(String itemName, double price, boolean isVeg, CuisineType cuisineType) {
        this.menuItemId = "ITEM-" + (++itemCount);
        this.itemName = itemName;
        this.price = price;
        this.isVeg = isVeg;
        this.cuisineType = cuisineType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }
}
