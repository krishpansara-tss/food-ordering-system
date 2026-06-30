package com.fooddelivery.repository;

import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantRepository {
    private Map<String, Restaurant> restaurantList = new HashMap<>();

    public void addRestaurant(Restaurant restaurant){
        restaurantList.put(restaurant.getRestaurantId(), restaurant);
    }

    public Restaurant findRestaurantById(String restaurantId){
        return restaurantList.get(restaurantId);
    }

    public Map<String, Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public MenuItem findMenuById(String itemId){
        for (Restaurant restaurant : restaurantList.values()) {
            for (MenuItem item : restaurant.getMenuItemList().values()) {
                if (item.getMenuItemId().equalsIgnoreCase(itemId)) return item;
            }
        }

        return null;
    }
}
