package com.fooddelivery.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<String, CartItem> cartItemMap = new HashMap<>();
    private String currentRestaurantId = null;

    public Map<String, CartItem> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(Map<String, CartItem> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }

    public String getCurrentRestaurantId() {
        return currentRestaurantId;
    }

    public void setCurrentRestaurantId(String currentRestaurantId) {
        this.currentRestaurantId = currentRestaurantId;
    }
}
