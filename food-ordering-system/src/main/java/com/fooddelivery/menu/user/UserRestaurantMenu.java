package com.fooddelivery.menu.user;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.exceptions.MenuItemNotFoundException;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.User;
import com.fooddelivery.services.CartService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserRestaurantMenu {
    private RestaurantService restaurantService;
    private CartService cartService;

    public UserRestaurantMenu(RestaurantService restaurantService, CartService cartService) {
        this.restaurantService = restaurantService;
        this.cartService = cartService;
    }

    public void userRestaurantMenu(User user, Scanner scanner) {
        if (!(user instanceof Customer)) {
            System.out.println("Error: Logged in user is not a Customer.");
            return;
        }

        Customer customer = (Customer) user;
        System.out.println("Welcome to Restaurant / Cart Menu!");

        while (true) {
            System.out.println("\n--- Customer Portal ---");
            System.out.println("1. View Available Restaurants (In your City)");
            System.out.println("2. All Restaurant List");
            System.out.println("3. View Restaurant Menu (All Items)");
            System.out.println("4. View Restaurant Menu by Cuisine");
            System.out.println("5. View Cart");
            System.out.println("6. Add Item to Cart");
            System.out.println("7. Remove from the cart");
            System.out.println("8. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 8);

            switch (choice) {
                // view available restaurant (in your city)
                case 1:
                    restaurantService.displayRestaurantsByCity(customer.getCity());
                    break;

                // display all restaurants
                case 2:
                    restaurantService.displayAllRestaurant();
                    break;

                // view restaurant menu
                case 3:
                    String restId = InputClass.readString(scanner, "Enter Restaurant ID (e.g., REST-1001): ").toUpperCase();
                    try {
                        restaurantService.displayRestaurantMenu(restId);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // view restaurant menu by cuisine
                case 4:
                    String restIdCuisine = InputClass.readString(scanner, "Enter Restaurant ID (e.g., REST-1001): ").toUpperCase();
                    System.out.println("Select Cuisine Type:");
                    CuisineType[] cTypes = CuisineType.values();
                    for (int i = 0; i < cTypes.length; i++) {
                        System.out.println((i + 1) + ". " + cTypes[i]);
                    }
                    int cChoice = InputClass.readInt(scanner, "Enter option (1-" + cTypes.length + "): ", 1, cTypes.length);
                    CuisineType cuisineToFilter = cTypes[cChoice - 1];

                    try {
                        restaurantService.displayRestaurantMenuByCuisine(restIdCuisine, cuisineToFilter);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // view cart
                case 5:
                    cartService.viewCart(customer);
                    break;

                // add item to the cart
                case 6:
                    String cartRestId = InputClass.readString(scanner, "Enter Restaurant ID: ").toUpperCase();
                    String itemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g., ITEM-1001): ").toUpperCase();
                    int quantity = InputClass.readInt(scanner, "Enter Quantity: ", 1, 100);

                    try {
                        cartService.addItemToCart(customer, cartRestId, itemId, quantity);
                    } catch (Exception e) {
                        System.out.println("Error adding item to cart: " + e.getMessage());
                    }
                    break;

                // modify the cart
                case 7:
                    String menuItemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g., ITEM-1001): ").toUpperCase();
                    int quantityToRemove = InputClass.readInt(scanner, "Enter Quantity to Remove from the Cart\n If you enter the quantity more then current quantity the item will be removed from the cart: ", 1, 100);
                    try{
                        cartService.removeItemFromCart(customer, menuItemId, quantityToRemove);
                    } catch (IllegalArgumentException | MenuItemNotFoundException e){
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected Error");
                    }
                    break;

                // logout
                case 8:
                    System.out.println("Back to Customer session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-8.");
            }
        }
    }
}
