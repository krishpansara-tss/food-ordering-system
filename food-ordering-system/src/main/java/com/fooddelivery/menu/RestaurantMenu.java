package com.fooddelivery.menu;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.exceptions.RestaurantNotFoundException;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.services.AdminService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.services.RestaurantService;
import com.fooddelivery.services.UserService;
import com.fooddelivery.util.InputClass;

import java.util.Map;
import java.util.Scanner;

public class RestaurantMenu {
    private RestaurantService restaurantService;
    private AdminService adminService;
    private OrderService orderService;

    public RestaurantMenu(RestaurantService restaurantService, AdminService adminService,OrderService orderService) {
        this.restaurantService = restaurantService;
        this.adminService = adminService;
        this.orderService = orderService;
    }

    public void restaurantMenu(Restaurant restaurant, Scanner scanner) {
        System.out.println("Welcome back, Owner: " + restaurant.getRestaurantName());

        while (true) {
            System.out.println("\n--- Restaurant Control Panel ---");
            System.out.println("1. Add Item to Restaurant Menu");
            System.out.println("2. Display Menu");
            System.out.println("3. Update Menu Item Details");
            System.out.println("4. View Restaurant Statistics");
            System.out.println("5. View Active Orders");
            System.out.println("6. Update Order Status");
            System.out.println("7. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 7);

            switch (choice) {
                // add item to restaurant menu
                case 1:
                    String itemName = InputClass.readString(scanner, "Enter Item Name: ");
                    double price = InputClass.readDouble(scanner, "Enter Price of the Item: ", 0);
                    boolean isVeg = InputClass.readBoolean(scanner, "Is it Veg? (true/false): ");

                    System.out.println("Select Cuisine Type:");
                    CuisineType[] cuisineTypes = CuisineType.values();
                    for (int i = 0; i < cuisineTypes.length; i++) {
                        System.out.println((i + 1) + ". " + cuisineTypes[i]);
                    }
                    int cuisineChoice = InputClass.readInt(scanner, "Enter option (1-" + cuisineTypes.length + "): ", 1, cuisineTypes.length);
                    CuisineType cuisineType = cuisineTypes[cuisineChoice - 1];

                    try {
                        restaurantService.addItemToRestaurant(restaurant.getRestaurantId(), itemName, price, isVeg, cuisineType);
                    } catch (Exception e) {
                        System.out.println("Error adding menu item: " + e.getMessage());
                    }
                    break;

                // display menu / available items
                case 2:
                    try {
                        restaurantService.displayRestaurantMenu(restaurant.getRestaurantId());
                    } catch (RestaurantNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                // update menu item
                case 3:
                    System.out.println("\n--- Update Menu Item Details ---");
                    String itemId = InputClass.readString(scanner, "Enter Menu Item ID (e.g. ITEM-1001): ").toUpperCase().trim();
                    MenuItem item = restaurant.getMenuItemList().get(itemId);
                    if (item == null) {
                        System.out.println("Menu item with ID [" + itemId + "] not found in your restaurant.");
                        break;
                    }

                    System.out.println("Current Details: " + item.getItemName() + " | Price: ₹" + item.getPrice() + " | Type: " + (item.isVeg() ? "VEG" : "NON-VEG") + " | Cuisine: " + item.getCuisineType());
                    
                    String newName = InputClass.readString(scanner, "Enter new Item Name (or type same name to keep): ");
                    double newPrice = InputClass.readDouble(scanner, "Enter new Price: ", 0);
                    boolean newVeg = InputClass.readBoolean(scanner, "Is it Veg? (true/false): ");

                    System.out.println("Select New Cuisine Type (Current: " + item.getCuisineType() + "):");
                    CuisineType[] cuisines = CuisineType.values();
                    for (int i = 0; i < cuisines.length; i++) {
                        System.out.println((i + 1) + ". " + cuisines[i]);
                    }
                    int choiceCuisine = InputClass.readInt(scanner, "Enter option (1-" + cuisines.length + "): ", 1, cuisines.length);
                    CuisineType newCuisine = cuisines[choiceCuisine - 1];

                    item.setItemName(newName);
                    item.setPrice(newPrice);
                    item.setVeg(newVeg);
                    item.setCuisineType(newCuisine);
                    System.out.println("Menu item updated successfully!");
                    break;

                // view restaurant statistics
                case 4:
                    adminService.displayRestaurantStatistics(restaurant.getRestaurantId());
                    break;

                // view active orders
                case 5:
                    orderService.displayOrdersForRestaurant(restaurant.getRestaurantId());
                    break;

                // update order status
                case 6:
                    System.out.println("\n--- Update Order Status ---");
                    String orderId = InputClass.readString(scanner, "Enter Order ID (e.g. ORD-1001): ").toUpperCase().trim();
                    Order order = orderService.findOrderById(orderId);
                    if (order == null || !order.getRestaurantId().equalsIgnoreCase(restaurant.getRestaurantId())) {
                        System.out.println("Active order with ID [" + orderId + "] not found for your restaurant.");
                        break;
                    }

                    System.out.println("Current Order Status: " + order.getOrderStatus());
                    System.out.println("Select New Status:");
                    System.out.println("1. PREPARING");
                    System.out.println("2. OUT_FOR_DELIVERY");
                    System.out.println("3. DELIVERED");
                    int statusChoice = InputClass.readInt(scanner, "Enter option (1-3): ", 1, 3);

                    OrderStatus newStatus;
                    if (statusChoice == 1) newStatus = OrderStatus.PREPARING;
                    else if (statusChoice == 2) newStatus = OrderStatus.OUT_FOR_DELIVERY;
                    else newStatus = OrderStatus.DELIVERED;

                    try {
                        orderService.updateOrderStatus(orderId, newStatus);
                    } catch (Exception e) {
                        System.out.println("Failed to update status: " + e.getMessage());
                    }
                    break;

                // logout
                case 7:
                    System.out.println("Logging out Restaurant Owner session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-7.");
            }
        }
    }
}
