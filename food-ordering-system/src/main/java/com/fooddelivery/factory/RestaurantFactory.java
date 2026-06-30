package com.fooddelivery.factory;

import com.fooddelivery.model.Restaurant;

public class RestaurantFactory {

    public static Restaurant createRestaurant(String restaurantName, String password, String phoneNumber, String city){
        return new Restaurant(restaurantName, password, phoneNumber, city);
    }
}
