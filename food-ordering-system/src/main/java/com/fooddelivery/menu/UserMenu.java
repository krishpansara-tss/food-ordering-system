package com.fooddelivery.menu;

import com.fooddelivery.interfaces.PaymentMode;
import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.User;
import com.fooddelivery.payment.CardPayment;
import com.fooddelivery.payment.CODPayment;
import com.fooddelivery.payment.UPIPayment;
import com.fooddelivery.services.CartService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserMenu {
    private RestaurantService restaurantService;
    private CartService cartService;
    private OrderService orderService;

    public UserMenu(RestaurantService restaurantService, CartService cartService, OrderService orderService) {
        this.restaurantService = restaurantService;
        this.cartService = cartService;
        this.orderService = orderService;
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
            System.out.println("1. View Available Restaurants");
            System.out.println("2. View Restaurant Menu");
            System.out.println("3. Add Item to Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Modify the cart");
            System.out.println("6. Place Order");
            System.out.println("7. View Order History");
            System.out.println("8. Expand Order by Id");
            System.out.println("9. All Restaurant List");
            System.out.println("10. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 10);

            switch (choice) {
                // view available restaurant (in your city)
                case 1:
                    restaurantService.displayRestaurantsByCity(customer.getCity());
                    break;

                // view restaurant menu
                case 2:
                    String restId = InputClass.readString(scanner, "Enter Restaurant ID (e.g., REST-1001): ").toUpperCase();
                    try {
                        restaurantService.displayRestaurantMenu(restId);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // add item to the cart
                case 3:
                    String cartRestId = InputClass.readString(scanner, "Enter Restaurant ID: ").toUpperCase();
                    String itemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g., ITEM-1001): ").toUpperCase();
                    int quantity = InputClass.readInt(scanner, "Enter Quantity: ", 1, 100);

                    try {
                        cartService.addItemToCart(customer, cartRestId, itemId, quantity);
                    } catch (Exception e) {
                        System.out.println("Error adding item to cart: " + e.getMessage());
                    }
                    break;

                // view cart
                case 4:
                    cartService.viewCart(customer);
                    break;

                // modify the cart
                case 5:
                    String menuItemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g., ITEM-1001): ").toUpperCase();
                    int quantityToRemove = InputClass.readInt(scanner, "Enter Quantity to Remove from the Cart\n If you enter the quantity more then current quantity the item will be removed from the cart: ", 1, 100);
                    cartService.removeItemFromCart(customer, menuItemId, quantityToRemove);
                    break;

                // place order
                case 6:
                    orderService.placeOrderFlow(customer, scanner);
                    break;

                // display all order history
                case 7:
                    orderService.displayOrderHistory(customer);
                    break;

                // display specific order detail
                case 8:
                    String orderId = InputClass.readString(scanner, "Enter Order ID (e.g., ORD-1001): ");
                    orderService.displayOrderById(customer, orderId);
                    break;

                // display all restaurants
                case 9:
                    restaurantService.displayAllRestaurant();
                    break;

                // logout
                case 10:
                    System.out.println("Logging out Customer session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-10.");
            }
        }
    }
}
