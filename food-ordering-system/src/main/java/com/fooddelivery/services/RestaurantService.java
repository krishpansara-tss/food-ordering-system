package com.fooddelivery.services;

import com.fooddelivery.exceptions.RestaurantNotFoundException;
import com.fooddelivery.factory.RestaurantFactory;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;

import java.util.Map;

public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(String restaurantName, String phoneNumber, String city){
        Restaurant restaurant = RestaurantFactory.createRestaurant(restaurantName, phoneNumber, city);

        restaurantRepository.addRestaurant(restaurant);
        System.out.println("Restaurant registered successfully! Assigned ID: " + restaurant.getRestaurantId());
        return restaurant;
    }

    public void displayAllRestaurant(){
        Map<String, Restaurant> restaurantMap = restaurantRepository.getRestaurantList();

        if(restaurantMap == null || restaurantMap.isEmpty()){
            System.out.println("No restaurant available.");
            return;
        }

        System.out.println("\n============== AVAILABLE RESTAURANTS ==============");
        for (Restaurant r : restaurantMap.values()) {
            System.out.println("[" + r.getRestaurantId() + "] " + r.getRestaurantName());
        }
        System.out.println("====================================================");
    }

    public void addItemToRestaurant(String restaurantId, String itemName, double price, boolean isVeg){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant Not Found: Restaurant with ID [" + restaurantId + "] not found.");
        }
        MenuItem menuItem = new MenuItem(itemName, price, isVeg);

        restaurant.addMenuItem(menuItem);

        System.out.println("Menu item with ID: " + menuItem.getMenuItemId() + " added successfully in Restaurant: " + restaurantId);
    }

    public void displayRestaurantMenu(String restaurantId){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant Not Found: Restaurant with ID [" + restaurantId + "] not found.");
        }

        Map<String, MenuItem> restaurantMenuItemList = restaurant.getMenuItemList();

        if(restaurantMenuItemList == null || restaurantMenuItemList.isEmpty()){
            System.out.println("Menu not available or might not be added.");
        }

        System.out.println("\n========= MENU FOR: " + restaurant.getRestaurantName().toUpperCase() + " =========");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "ID", "Item Name", "Price", "Type");
        System.out.println("---------------------------------------------------------");
        for(MenuItem item : restaurantMenuItemList.values()){
            String type = item.isVeg() ? "VEG" : "NON-VEG";
            System.out.printf("%-10s %-20s ₹%-9.2f %-10s\n", item.getMenuItemId(), item.getItemName(), item.getPrice(), type);
        }
        System.out.println("=========================================================");
    }
}
