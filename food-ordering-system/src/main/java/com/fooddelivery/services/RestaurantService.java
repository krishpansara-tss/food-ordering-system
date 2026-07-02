package com.fooddelivery.services;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.exceptions.*;
import com.fooddelivery.factory.RestaurantFactory;
import com.fooddelivery.model.Cuisine;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.RestaurantRepository;

import java.util.Map;
import java.util.Set;

public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant createRestaurant(String restaurantName, String password, String phoneNumber, String city,  String locality){
        Restaurant restaurant = RestaurantFactory.createRestaurant(restaurantName, password, phoneNumber, city, locality);

        restaurantRepository.addRestaurant(restaurant);
        System.out.println("Restaurant registered successfully! Assigned ID: " + restaurant.getRestaurantId());
        return restaurant;
    }

    public Restaurant loginIntoRestaurant(String restaurantId, String password){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId.toUpperCase());
        if(restaurant == null){
            throw new RestaurantNotFoundException("Login Failed: Restaurant with ID " + restaurantId + " not found.");
        }

        if(!(restaurant.getPassword().equals(password))){
            throw new InvalidPasswordException("Login Failed: Password don't match");
        }

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
            System.out.println("[" + r.getRestaurantId() + "] " + r.getRestaurantName() + " ("  + r.getLocality() + " | " + r.getCity() + ")");
        }
        System.out.println("====================================================");
    }

    public void displayRestaurantsByCity(String city){
        Map<String, Restaurant> restaurantMap = restaurantRepository.getRestaurantList();

        if(restaurantMap == null || restaurantMap.isEmpty()){
            System.out.println("No restaurant available.");
            return;
        }

        boolean found = false;
        System.out.println("\n============== AVAILABLE RESTAURANTS IN " + city.toUpperCase() + " ==============");
        for (Restaurant r : restaurantMap.values()) {
            if (r.getCity().equalsIgnoreCase(city)) {
                System.out.println(r.getRestaurantId() + " -> " + r.getRestaurantName() + "  | Address: " + r.getLocality() + " " + r.getCity());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No restaurants found in your city: " + city);
        }
        System.out.println("====================================================");
    }


    public void addItemToRestaurant(String restaurantId, String itemName, double price, boolean isVeg, CuisineType cuisineType){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant Not Found: Restaurant with ID:" + restaurantId + " not found.");
        }

        for(MenuItem menuItem : restaurant.getMenuItemList().values()){
            if(menuItem.getItemName().equalsIgnoreCase(itemName.trim())){
                throw new ItemAlreadyExist("Item having name: " + itemName + " is already exists");
            }
        }

        Set<Cuisine> cuisineSet = restaurant.getCuisines();

        MenuItem menuItem = new MenuItem(itemName, price, isVeg, cuisineType);

        restaurant.addMenuItem(menuItem);

        System.out.println("Menu item with ID: " + menuItem.getMenuItemId() + " added successfully (Cuisine: " + cuisineType + ") in Restaurant: " + restaurantId);
    }

    public void displayRestaurantMenu(String restaurantId){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant Not Found: Restaurant with ID: " + restaurantId + " not found.");
        }

        Map<String, MenuItem> restaurantMenuItemList = restaurant.getMenuItemList();

        if(restaurantMenuItemList == null || restaurantMenuItemList.isEmpty()){
            System.out.println("Menu not available or might not be added.");
            return;
        }

        System.out.println("\n========= MENU FOR: " + restaurant.getRestaurantName().toUpperCase() + " =========");
        System.out.printf("%-10s %-25s %-10s %-10s %-15s\n", "ID", "Item Name", "Price", "Type", "Cuisine");
        System.out.println("-------------------------------------------------------------------------");
        for(MenuItem item : restaurantMenuItemList.values()){
            String type = item.isVeg() ? "VEG" : "NON-VEG";
            System.out.printf("%-10s %-25s ₹%-9.2f %-10s %-15s\n", item.getMenuItemId(), item.getItemName(), item.getPrice(), type, item.getCuisineType());
        }
        System.out.println("=========================================================================");
    }

    public void displayRestaurantMenuByCuisine(String restaurantId, CuisineType cuisineType){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);
        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant Not Found: Restaurant with ID: " + restaurantId + " not found.");
        }

        Map<String, MenuItem> restaurantMenuItemList = restaurant.getMenuItemList();

        if(restaurantMenuItemList == null || restaurantMenuItemList.isEmpty()){
            System.out.println("Menu not available or might not be added.");
            return;
        }

        boolean found = false;
        System.out.println("\n========= " + cuisineType + " MENU FOR: " + restaurant.getRestaurantName().toUpperCase() + " =========");
        System.out.printf("%-10s %-25s %-10s %-10s %-15s\n", "ID", "Item Name", "Price", "Type", "Cuisine");
        System.out.println("-------------------------------------------------------------------------");
        for(MenuItem item : restaurantMenuItemList.values()){
            if (item.getCuisineType() == cuisineType) {
                String type = item.isVeg() ? "VEG" : "NON-VEG";
                System.out.printf("%-10s %-25s ₹%-9.2f %-10s %-15s\n", item.getMenuItemId(), item.getItemName(), item.getPrice(), type, item.getCuisineType());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No items found under " + cuisineType + " cuisine.");
        }
        System.out.println("=========================================================================");
    }

    public void getContactDetailsOfRestaurant(String restaurantId){
        Restaurant restaurant = restaurantRepository.findRestaurantById(restaurantId);

        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant having ID: " + restaurantId + " not found.");
        }

        System.out.println("------ Contact Detail ------");
        System.out.println("Restaurant ID        : " + restaurantId);
        System.out.println("Restaurant Name      : " + restaurant.getRestaurantName());
        System.out.println("Contact Number       : " + restaurant.getPhoneNumber());
        System.out.println("Restaurant City      : " + restaurant.getCity());
        System.out.println("Restaurant Locality  : " + restaurant.getLocality());
        System.out.println("-----------------------------");
    }

    public void removeMenuItem(Restaurant restaurant, String menuId){
        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant not found.");
        }

        Map<String, MenuItem> menuItemMap = restaurant.getMenuItemList();
        if(menuItemMap.isEmpty()){
            System.out.println("No menu item added to the restaurant.");
        }

        MenuItem menuItem = menuItemMap.get(menuId);
        if(menuItem == null){
            throw new MenuItemNotFoundException("Menu Item having ID: " + menuId + " is not found.");
        }

        menuItemMap.remove(menuId);
        System.out.println("Menu Item having ID: " + menuId + " is removed Successfully.");
    }

    public void removeRestaurant(Restaurant restaurant){
        if(restaurant == null){
            throw new RestaurantNotFoundException("Restaurant not found.");
        }

       restaurantRepository.removeRestaurant(restaurant.getRestaurantId());
        System.out.println("Restaurant is removed Successfully.");
    }
}
