package com.fooddelivery.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Restaurant {
    private static long restaurantCount = 1000;
    private String restaurantId;
    private String password;
    private String restaurantName;
    private String phoneNumber;
    private String city;
    private Map<String, MenuItem> menuItemList = new HashMap();

    public Restaurant(String restaurantName, String password, String phoneNumber, String city) {
        this.restaurantId = "REST-"  + (++restaurantCount);
        this.password = password;
        this.restaurantName = restaurantName;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Map<String, MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(Map<String, MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    public void addMenuItem(MenuItem menuItem){
        menuItemList.put(menuItem.getMenuItemId(), menuItem);
    }
}
