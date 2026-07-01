package com.fooddelivery.menu;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.model.Admin;
import com.fooddelivery.model.User;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.services.UserService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class SuperAdminMenu {
    private AdminService adminService;
    private UserService userService;
    private RestaurantService restaurantService;

    public SuperAdminMenu(AdminService adminService, UserService userService, RestaurantService restaurantService) {
        this.adminService = adminService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public void superAdminMenu(User admin, Scanner scanner) {
        if (!(admin instanceof Admin)) {
            System.out.println("Error: Logged in user is not a Admin.");
            return;
        }

        System.out.println("Welcome back, Admin: " + admin.getUserName());

        while (true) {
            System.out.println("\n--- Admin Control Panel ---");
            System.out.println("1. Display all the Admin");
            System.out.println("2 View All Registered Users");
            System.out.println("3. View Global App Statistics");
            System.out.println("4. Display all Restaurant");
            System.out.println("5. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 5);

            switch (choice) {

                // display all admin
                case 1:
                    adminService.displayAllAdmin();
                    break;

                // display all user
                case 2:
                    userService.displayAllUser();
                    break;

                // display app statistic
                case 3:
                    adminService.displayAppStatistics();
                    break;

                // display all restaurant
                case 4:
                    restaurantService.displayAllRestaurant();
                    break;

                // logout
                case 5:
                    admin = null;
                    System.out.println("Logging out Admin session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-5.");

            }
        }
    }
}
