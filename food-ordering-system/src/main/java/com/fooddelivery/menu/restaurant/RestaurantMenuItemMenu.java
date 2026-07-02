package com.fooddelivery.menu.restaurant;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.exceptions.RestaurantNotFoundException;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class RestaurantMenuItemMenu {
    private RestaurantService restaurantService;

    public RestaurantMenuItemMenu(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    public void restaurantMenuItemMenu(Restaurant restaurant, Scanner scanner) {
        System.out.println("Welcome to Menu Options");

        while (true) {
            System.out.println("\n--- Restaurant Control Panel ---");
            System.out.println("1. Display Menu");
            System.out.println("2. Add Item to Restaurant Menu");
            System.out.println("3. Update Menu Item Details");
            System.out.println("4. Remove Menu Item from the Restaurant");
            System.out.println("5. Back to Main Restaurant Menu");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 5);

            switch (choice) {
                // display menu / available items
                case 1:
                    try {
                        restaurantService.displayRestaurantMenu(restaurant.getRestaurantId());
                    } catch (RestaurantNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;


                // add item to restaurant menu
                case 2:
                    String itemName = InputClass.readString(scanner, "Enter Item Name: ");
                    double price = InputClass.readDouble(scanner, "Enter Price of the Item: ", 0);
                    boolean isVeg = InputClass.readBoolean(scanner, "Is it Veg? (true/false): ");

                    System.out.println("Select Cuisine Type:");
                    CuisineType[] cuisineTypes = CuisineType.values();
                    for (int i = 0; i < cuisineTypes.length; i++) {
                        System.out.println((i + 1) + ". " + cuisineTypes[i]);
                    }
                    int cuisineChoice = InputClass.readInt(scanner, "Enter option (1-" + cuisineTypes.length + "): ", 1, cuisineTypes.length);
                    CuisineType cuisineType = cuisineTypes[cuisineChoice - 1];

                    try {
                        restaurantService.addItemToRestaurant(restaurant.getRestaurantId(), itemName, price, isVeg, cuisineType);
                    } catch (Exception e) {
                        System.out.println("Error adding menu item: " + e.getMessage());
                    }
                    break;


                // update menu item
                case 3:
                    System.out.println("\n--- Update Menu Item Details ---");
                    String itemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g. ITEM-1001): ").toUpperCase().trim();
                    MenuItem item = restaurant.getMenuItemList().get(itemId);
                    if (item == null) {
                        System.out.println("Menu item with ID [" + itemId + "] not found in your restaurant.");
                        break;
                    }

                    System.out.println("Current Details: " + item.getItemName() + " | Price: ₹" + item.getPrice() + " | Type: " + (item.isVeg() ? "VEG" : "NON-VEG") + " | Cuisine: " + item.getCuisineType());

                    String newName = InputClass.readString(scanner, "Enter new Item Name (or type same name to keep): ");
                    double newPrice = InputClass.readDouble(scanner, "Enter new Price: ", 0);
                    boolean newVeg = InputClass.readBoolean(scanner, "Is it Veg? (true/false): ");

                    System.out.println("Select New Cuisine Type (Current: " + item.getCuisineType() + "):");
                    CuisineType[] cuisines = CuisineType.values();
                    for (int i = 0; i < cuisines.length; i++) {
                        System.out.println((i + 1) + ". " + cuisines[i]);
                    }
                    int choiceCuisine = InputClass.readInt(scanner, "Enter option (1-" + cuisines.length + "): ", 1, cuisines.length);
                    CuisineType newCuisine = cuisines[choiceCuisine - 1];

                    item.setItemName(newName);
                    item.setPrice(newPrice);
                    item.setVeg(newVeg);
                    item.setCuisineType(newCuisine);
                    System.out.println("Menu item updated successfully!");
                    break;

                // remove menu item from the restaurant
                case 4:
                    String menuItemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g. ITEM-1001): ").toUpperCase().trim();
                    try{
                        restaurantService.removeMenuItem(restaurant, menuItemId);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                // logout
                case 5:
                    System.out.println("Back to Main Restaurant Owner session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-5.");
            }
        }
    }
}