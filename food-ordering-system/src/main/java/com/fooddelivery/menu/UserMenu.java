package com.fooddelivery.menu;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.exceptions.MenuItemNotFoundException;
import com.fooddelivery.exceptions.UserNotFoundException;
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
import com.fooddelivery.services.UserService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserMenu {
    private RestaurantService restaurantService;
    private CartService cartService;
    private OrderService orderService;
    private UserService userService;

    public UserMenu(RestaurantService restaurantService, CartService cartService, OrderService orderService, UserService userService) {
        this.restaurantService = restaurantService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
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
            System.out.println("1. View Available Restaurants (In your City)");
            System.out.println("2. View Restaurant Menu (All Items)");
            System.out.println("3. View Restaurant Menu by Cuisine");
            System.out.println("4. Add Item to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Remove from the cart");
            System.out.println("7. Place Order");
            System.out.println("8. View Order History");
            System.out.println("9. Expand Order by Id");
            System.out.println("10. Check Order Status");
            System.out.println("11. All Restaurant List");
            System.out.println("12. Your Statistic (Spend, save etc.)");
            System.out.println("13. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 13);

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

                // view restaurant menu by cuisine
                case 3:
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

                // add item to the cart
                case 4:
                    String cartRestId = InputClass.readString(scanner, "Enter Restaurant ID: ").toUpperCase();
                    String itemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g., ITEM-1001): ").toUpperCase();
                    int quantity = InputClass.readInt(scanner, "Enter Quantity: ", 1, 10);

                    try {
                        cartService.addItemToCart(customer, cartRestId, itemId, quantity);
                    } catch (Exception e) {
                        System.out.println("Error adding item to cart: " + e.getMessage());
                    }
                    break;

                // view cart
                case 5:
                    cartService.viewCart(customer);
                    break;

                // modify the cart
                case 6:
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

                // place order
                case 7:
                    System.out.println("\nSelect Payment Method:");
                    System.out.println("1. Card Payment");
                    System.out.println("2. UPI Payment");
                    System.out.println("3. Cash on Delivery (COD)");

                    int paymentChoice = InputClass.readInt(scanner, "Choose option (1-3): ", 1, 3);
                    PaymentMode paymentMode;

                    switch (paymentChoice) {
                        case 1:
                            paymentMode = new CardPayment();
                            break;
                        case 2:
                            paymentMode = new UPIPayment();
                            break;
                        case 3:
                            paymentMode = new CODPayment();
                            break;
                        default:
                            System.out.println("Invalid payment choice. Order cancelled.");
                            return;
                    }
                    orderService.placeOrderFlow(customer, paymentMode);
                    break;

                // display all order history
                case 8:
                    orderService.displayOrderHistory(customer);
                    break;

                // display specific order detail
                case 9:
                    String orderId = InputClass.readString(scanner, "Enter Order ID (e.g., ORD-1001): ");
                    orderService.displayOrderById(customer, orderId);
                    break;

                // order status
                case 10:
                    orderId = InputClass.readString(scanner, "Enter Order ID (e.g., ORD-1001): ");
                    try{
                        orderService.getYourOrderStatus(orderId);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                // display all restaurants
                case 11:
                    restaurantService.displayAllRestaurant();
                    break;

                // get statistic
                case 12:
                    try{
                        userService.getCustomerStatistic(customer);
                    }catch (UserNotFoundException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                // logout
                case 13:
                    System.out.println("Logging out Customer session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-13.");
            }
        }
    }
}
