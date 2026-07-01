package com.fooddelivery.menu.user;

import com.fooddelivery.exceptions.UserNotFoundException;
import com.fooddelivery.model.Address;
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
            System.out.println("\n--- Customer Profile Portal ---");
            System.out.println("1. View Saved Addresses");
            System.out.println("2. Add New Address");
            System.out.println("3. Your Statistic (Spend, save etc.)");
            System.out.println("4. Back to Customer session");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 4);

            switch (choice) {
                // view saved addresses
                case 1:
                    System.out.println("\n--- Your Saved Addresses ---");
                    for (int i = 0; i < customer.getAddresses().size(); i++) {
                        System.out.println((i + 1) + ". " + customer.getAddresses().get(i));
                    }
                    break;

                // add new address
                case 2:
                    System.out.println("\n--- Add New Address ---");
                    String label = InputClass.readString(scanner, "Enter label (e.g., Home, Work): ");
                    String street = InputClass.readString(scanner, "Enter street name: ");
                    String city = InputClass.readString(scanner, "Enter city: ");
                    try {
                        userService.addAddressToCustomer(customer, label, street, city);
                    } catch (Exception e) {
                        System.out.println("Error adding address: " + e.getMessage());
                        break;
                    }
                    System.out.println("Address added successfully!");
                    break;

                // get statistic
                case 3:
                    try{
                        userService.getCustomerStatistic(customer);
                    }catch (UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                // logout
                case 4:
                    System.out.println("Back to Customer session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-4.");
            }
        }
    }
}
