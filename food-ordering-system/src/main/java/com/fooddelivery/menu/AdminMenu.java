package com.fooddelivery.menu;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.model.Admin;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.User;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.services.UserService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class AdminMenu {
    private AdminService adminService;
    private UserService userService;
    private RestaurantService restaurantService;

    public AdminMenu(AdminService adminService, UserService userService, RestaurantService restaurantService) {
        this.adminService = adminService;
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    public void adminMenu(User admin, Scanner scanner) {
        if (!(admin instanceof Admin)) {
            System.out.println("Error: Logged in user is not a Admin.");
            return;
        }

        System.out.println("Welcome back, Admin: " + admin.getUserName());

        while (true) {
            System.out.println("\n--- Admin Control Panel ---");
            System.out.println("1. Add new Admin");
            System.out.println("2. Display all the Admin");
            System.out.println("3. View All Registered Users");
            System.out.println("4. View Global App Statistics");
            System.out.println("5. Display all Restaurant");
            System.out.println("6. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 6);

            switch (choice) {
                // add new admin
                case 1:
                    if(admin.getUserType() != UserType.SUPER_ADMIN){
                        System.out.println("You can't have access to add new Admins.");
                        break;
                    }
                    String username = InputClass.readString(scanner, "Enter admin Name: ");
                    String password = InputClass.readString(scanner, "Enter password for admin: ");
                    String phoneNumber = InputClass.readString(scanner, "Enter Phone Number: ");
                    String adminCity = InputClass.readString(scanner, "Enter City: ");

                    try {
                        User newAdmin = adminService.addAdmin(username, password, phoneNumber, adminCity, UserType.ADMIN);
                        System.out.println("Please note down your admin ID for future logins: " + newAdmin.getUserId());
                    } catch (Exception e) {
                        System.out.println("Registration failed: " + e.getMessage());
                    }
                    break;

                // display all admin
                case 2:
                    adminService.displayAllAdmin();
                    break;

                // display all user
                case 3:
                    userService.displayAllUser();
                    break;

                // display app statistic
                case 4:
                    adminService.displayAppStatistics();
                    break;

                // display all restaurant
                case 5:
                    restaurantService.displayAllRestaurant();
                    break;

                // logout
                case 6:
                    System.out.println("Logging out Admin session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-5.");

            }
        }
    }
}
