package com.fooddelivery.menu;

import com.fooddelivery.menu.user.UserOrderMenu;
import com.fooddelivery.menu.user.UserProfileMenu;
import com.fooddelivery.menu.user.UserRestaurantMenu;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.User;
import com.fooddelivery.services.*;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserMenu {
    private UserRestaurantMenu userRestaurantMenu;
    private UserOrderMenu userOrderMenu;
    private UserProfileMenu userProfileMenu;

    public UserMenu(RestaurantService restaurantService, CartService cartService, OrderService orderService, UserService userService) {
        this.userRestaurantMenu = new UserRestaurantMenu(restaurantService, cartService);
        this.userOrderMenu = new UserOrderMenu(orderService, userService);
        this.userProfileMenu = new UserProfileMenu(userService);
    }

    public void userMenu(User user, Scanner scanner) {
        if (!(user instanceof Customer)) {
            System.out.println("Error: Logged in user is not a Customer.");
            return;
        }
        Customer customer = (Customer) user;
        System.out.println("Welcome, " + customer.getUserName() + "!");

        while (true) {
            System.out.println("\n--- Customer Portal ---");
            System.out.println("1. Restaurant & Cart Options");
            System.out.println("2. Order Options");
            System.out.println("3. Profile & Statistics");
            System.out.println("4. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 4);

            switch (choice) {
                // restaurant & cart option
                case 1:
                    userRestaurantMenu.userRestaurantMenu(customer, scanner);
                    break;

                // order option
                case 2:
                    userOrderMenu.userOrderMenu(customer, scanner);
                    break;

                // profile
                case 3:
                    userProfileMenu.userProfileMenu(customer, scanner);
                    break;

                // back to main menu
                case 4:
                    System.out.println("Logging out Customer session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-4.");
            }
        }
    }
}
