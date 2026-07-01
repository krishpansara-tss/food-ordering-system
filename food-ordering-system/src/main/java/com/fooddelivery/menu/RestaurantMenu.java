package com.fooddelivery.menu;

import com.fooddelivery.menu.restaurant.RestaurantMenuItemMenu;
import com.fooddelivery.menu.restaurant.RestaurantOrderMenu;
import com.fooddelivery.menu.restaurant.RestaurantProfileMenu;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class RestaurantMenu {
    private RestaurantMenuItemMenu menuItemMenu;
    private RestaurantOrderMenu orderMenu;
    private RestaurantProfileMenu profileMenu;

    public RestaurantMenu(RestaurantService restaurantService, AdminService adminService, OrderService orderService) {
        this.menuItemMenu = new RestaurantMenuItemMenu(restaurantService, adminService, orderService);
        this.orderMenu = new RestaurantOrderMenu(orderService);
        this.profileMenu = new RestaurantProfileMenu(restaurantService, adminService);
    }

    public void restaurantMenu(Restaurant restaurant, Scanner scanner) {
        System.out.println("Welcome back, Owner: " + restaurant.getRestaurantName());

        while (true) {
            System.out.println("\n--- Restaurant Control Panel ---");
            System.out.println("1. Manage Menu Items");
            System.out.println("2. Manage Orders");
            System.out.println("3. Manage Profile & Statistics");
            System.out.println("4. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 4);

            switch (choice) {
                // menu related
                case 1:
                    menuItemMenu.restaurantMenuItemMenu(restaurant, scanner);
                    break;

                // manage orders
                case 2:
                    orderMenu.restaurantOrderMenu(restaurant, scanner);
                    break;

                // profile
                case 3:
                    profileMenu.restaurantProfileMenu(restaurant, scanner);
                    break;

                // log out
                case 4:
                    System.out.println("Logging out Restaurant Owner session...");
                    return;
                default:
                    System.out.println("Invalid choice. Please select from 1-4.");
            }
        }
    }
}
