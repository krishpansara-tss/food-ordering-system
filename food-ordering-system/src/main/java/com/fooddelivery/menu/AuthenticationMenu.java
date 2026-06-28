package com.fooddelivery.menu;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;
import com.fooddelivery.services.*;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class AuthenticationMenu {
    private UserService userService;
    private RestaurantService restaurantService;
    private CartService cartService;
    private OrderService orderService;
    private AdminService adminService;
    private DeliveryPartnerService deliveryPartnerService;

    public AuthenticationMenu(UserService userService, RestaurantService restaurantService, 
                              CartService cartService, OrderService orderService, AdminService adminService,
                              DeliveryPartnerService deliveryPartnerService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.adminService = adminService;
        this.deliveryPartnerService = deliveryPartnerService;
    }

    public void authenticationMenu() {
        Scanner scanner = new Scanner(System.in);
        AdminMenu adminMenu = new AdminMenu(adminService, userService, restaurantService);
        UserMenu userMenu = new UserMenu(restaurantService, cartService, orderService);
        RestaurantMenu restaurantMenu = new RestaurantMenu(restaurantService, adminService, orderService);
        DeliveryPartnerMenu deliveryPartnerMenu = new DeliveryPartnerMenu(orderService, deliveryPartnerService);

        while (true) {
            System.out.println("\n=============================================");
            System.out.println("   WELCOME TO THE FOOD DELIVERY APPLICATION  ");
            System.out.println("=============================================");
            System.out.println("1. Login (Customer/Driver/Admin)");
            System.out.println("2. Register as a Customer");
            System.out.println("3. Join as a Delivery Partner");
            System.out.println("4. Add Restaurant to Application");
            System.out.println("5. Go to your Restaurant");
            System.out.println("6. Exit Program");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 6);

            switch (choice) {
                // login
                case 1:
                    String userId = InputClass.readString(scanner, "Enter User/Admin ID (e.g. CUST-1001): ").toUpperCase();
                    String password = InputClass.readString(scanner, "Enter Password: ");
                    
                    System.out.println("\nSelect Account Type for Login:");
                    System.out.println("1. Customer");
                    System.out.println("2. Delivery Partner");
                    System.out.println("3. Admin");
                    int typeChoice = InputClass.readInt(scanner, "Enter choice (1-3): ", 1, 3);

                    UserType userType;

                    if (typeChoice == 1) userType = UserType.CUSTOMER;
                    else if (typeChoice == 2) userType = UserType.DELIVERY_PARTNER;
                    else userType = UserType.ADMIN;

                    try {
                        User user = userService.login(userId, password, userType);
                        if (userType == UserType.CUSTOMER) {
                            userMenu.userMenu(user, scanner);
                        } else if (userType == UserType.DELIVERY_PARTNER) {
                            deliveryPartnerMenu.deliveryPartnerMenu((DeliveryPartner) user, scanner);
                        } else if (userType == UserType.ADMIN) {
                            adminMenu.adminMenu(user, scanner);
                        }
                    } catch (Exception e) {
                        System.out.println("\nLogin Failed: " + e.getMessage());
                    }
                    break;

                // register as a customer
                case 2:
                    String customerName = InputClass.readString(scanner, "Enter Customer Name: ");
                    String customerPassword = InputClass.readString(scanner, "Enter Password: ");
                    String customerPhone = InputClass.readString(scanner, "Enter Phone Number: ");
                    String customerCity = InputClass.readString(scanner, "Enter City: ");

                    try {
                        User registered = userService.registerUser(UserType.CUSTOMER, customerName, customerPassword, customerPhone, customerCity);
                        System.out.println("Please note down your User ID for future logins: " + registered.getUserId());
                    } catch (Exception e) {
                        System.out.println("Registration failed: " + e.getMessage());
                    }
                    break;

                // register as a delivery partner
                case 3:
                    String dpName = InputClass.readString(scanner, "Enter Delivery Partner Name: ");
                    String dpPassword = InputClass.readString(scanner, "Enter Password: ");
                    String dpPhone = InputClass.readString(scanner, "Enter Phone Number: ");
                    String dpCity = InputClass.readString(scanner, "Enter City: ");

                    try {
                        User registered = userService.registerUser(UserType.DELIVERY_PARTNER, dpName, dpPassword, dpPhone, dpCity);
                        System.out.println("Please note down your Partner ID for future logins: " + registered.getUserId());
                    } catch (Exception e) {
                        System.out.println("Failed to join: " + e.getMessage());
                    }
                    break;

                // add restaurant
                case 4:
                    String restaurantName = InputClass.readString(scanner, "Enter Restaurant Name: ");
                    String phoneNumber = InputClass.readString(scanner, "Enter Phone Number: ");
                    String city = InputClass.readString(scanner, "Enter City: ");
                    try {
                        Restaurant restaurant = restaurantService.createRestaurant(restaurantName, phoneNumber, city);
                        System.out.println("Please note down your Restaurant ID for future logins: " + restaurant.getRestaurantId());
                    }catch (Exception e){
                        System.out.println("Failed to join with us: " + e.getMessage());
                    }
                    break;

                // go to restaurant
                case 5:
                    String restaurantId = InputClass.readString(scanner, "Enter Restaurant ID (e.g. REST-1001): ").toUpperCase();

                    try{
                        Restaurant restaurant = restaurantService.loginIntoRestaurant(restaurantId);
                        restaurantMenu.restaurantMenu(restaurant, scanner);
                    } catch (Exception e) {
                        System.out.println("Login Failed: " + e.getMessage());
                    }
                    break;

                // exit
                case 6:
                    System.out.println("Thank you for using the Food Delivery Application. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please select from 1-6.");
            }
        }
    }
}
