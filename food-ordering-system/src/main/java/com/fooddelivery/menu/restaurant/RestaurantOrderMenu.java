package com.fooddelivery.menu.restaurant;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class RestaurantOrderMenu {
    private OrderService orderService;

    public RestaurantOrderMenu(OrderService orderService) {
        this.orderService = orderService;
    }

    public void restaurantOrderMenu(Restaurant restaurant, Scanner scanner) {
        System.out.println("Welcome to Order Menu");

        while (true) {
            System.out.println("\n--- Restaurant Control Panel ---");
            System.out.println("1. View All Orders");
            System.out.println("2. View Active orders");
            System.out.println("3. Update Order Status");
            System.out.println("4. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 4);

            switch (choice) {
                // view all orders
                case 1:
                    orderService.displayAllOrdersForRestaurant(restaurant.getRestaurantId());
                    break;

                // active
                case 2:
                    orderService.displayActiveOrdersForRestaurant(restaurant.getRestaurantId());
                    break;

                // update order status
                case 3:
                    System.out.println("\n--- Update Order Status ---");
                    String orderId = InputClass.readString(scanner, "Enter Order ID (e.g. ORD-1001): ").toUpperCase().trim();

                    Order order = orderService.findOrderById(orderId);
                    if (order == null || !order.getRestaurantId().equalsIgnoreCase(restaurant.getRestaurantId())) {
                        System.out.println("Active order with ID: " + orderId + " not found for your restaurant.");
                        break;
                    }

                    OrderStatusType current = order.getOrderStatus().getStatus();

                    if(current != OrderStatusType.APPROVED_BY_RESTAURANT){
                        System.out.println("Current Order Status: " + current);
                        System.out.println("You don't have access for further status update");
                        break;
                    }

                    System.out.println("\nCurrent Order: " + order.getOrderId()
                            + "  |  Current Order Status:: " + current);

                    OrderStatusType next = null;

                    next = OrderStatusType.READY_FOR_DELIVERY;
                    boolean changeStatus = InputClass.readBoolean(scanner, "Do you want to change order status to " + next + "? (true/false): ");

                    if(changeStatus) {
                        try {
                            orderService.updateOrderStatus(order.getOrderId());
                        } catch (Exception e) {
                            System.out.println("Failed to update status: " + e.getMessage());
                        }
                    }else{
                        System.out.println("No changes are made to the Order Status");
                    }
                    break;

                // logout
                case 4:
                    System.out.println("Back to Main Restaurant Owner session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-4.");
            }
        }
    }
}
