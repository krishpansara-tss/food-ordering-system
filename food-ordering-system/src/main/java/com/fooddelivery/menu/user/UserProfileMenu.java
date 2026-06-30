package com.fooddelivery.menu.user;

import com.fooddelivery.exceptions.UserNotFoundException;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.User;
import com.fooddelivery.services.UserService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserProfileMenu {
    private UserService userService;

    public UserProfileMenu(UserService userService) {
        this.userService = userService;
    }

    public void userProfileMenu(User user, Scanner scanner) {
        Customer customer = (Customer) user;
        System.out.println("Welcome to Profile Menu!");

        System.out.println("Welcome, " + customer.getUserName() + "!");

        while (true) {
            System.out.println("\n--- Customer Portal ---");
            System.out.println("1. Your Statistic (Spend, save etc.)");
            System.out.println("2. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 13);

            switch (choice) {

                // get statistic
                case 1:
                    try{
                        userService.getCustomerStatistic(customer);
                    }catch (UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                // logout
                case 2:
                    System.out.println("Back to Customer session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-2.");
            }
        }
    }
}
