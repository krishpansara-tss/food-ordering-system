package com.fooddelivery.menu.restaurant;


import com.fooddelivery.exceptions.RestaurantNotFoundException;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class RestaurantProfileMenu {
    private RestaurantService restaurantService;
    private AdminService adminService;

    public RestaurantProfileMenu(RestaurantService restaurantService, AdminService adminService) {
        this.restaurantService = restaurantService;
        this.adminService = adminService;
    }

    public void restaurantProfileMenu(Restaurant restaurant, Scanner scanner) {
        System.out.println("Welcome to Profile Menu");

        while (true) {
            System.out.println("\n--- Restaurant Control Panel ---");
            System.out.println("1. Display Menu");
            System.out.println("2. View Restaurant Statistics");
            System.out.println("3. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 3);

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

                // view restaurant statistics
                case 2:
                    adminService.displayRestaurantStatistics(restaurant.getRestaurantId());
                    break;

                // logout
                case 3:
                    System.out.println("Back to Main Restaurant Owner session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-3.");
            }
        }
    }
}