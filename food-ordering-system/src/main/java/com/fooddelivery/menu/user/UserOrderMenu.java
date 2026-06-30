package com.fooddelivery.menu.user;


import com.fooddelivery.interfaces.PaymentMode;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.User;
import com.fooddelivery.payment.CODPayment;
import com.fooddelivery.payment.CardPayment;
import com.fooddelivery.payment.UPIPayment;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserOrderMenu {
    private OrderService orderService;

    public UserOrderMenu(OrderService orderService) {
        this.orderService = orderService;
    }

    public void userOrderMenu(User user, Scanner scanner) {
        Customer customer = (Customer) user;
        System.out.println("Welcome to Order Menu!");

        while (true) {
            System.out.println("\n--- Customer Portal ---");
            System.out.println("1. Place Order");
            System.out.println("2. View Order History");
            System.out.println("3. Expand Order by Id");
            System.out.println("4. Check Order Status");
            System.out.println("5. Log out");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 5);

            switch (choice) {
                // place order
                case 1:
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
                case 2:
                    orderService.displayOrderHistory(customer);
                    break;

                // display specific order detail
                case 3:
                    String orderId = InputClass.readString(scanner, "Enter Order ID (e.g., ORD-1001): ");
                    orderService.displayOrderById(customer, orderId);
                    break;

                // order status
                case 4:
                    orderId = InputClass.readString(scanner, "Enter Order ID (e.g., ORD-1001): ");
                    try{
                        orderService.getYourOrderStatus(orderId);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                // logout
                case 5:
                    System.out.println("Back to the Customer Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-5.");
            }
        }
    }
}
