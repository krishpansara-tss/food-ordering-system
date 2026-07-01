package com.fooddelivery.menu.user;


import com.fooddelivery.interfaces.PaymentMode;
import com.fooddelivery.model.Address;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.User;
import com.fooddelivery.payment.CODPayment;
import com.fooddelivery.payment.CardPayment;
import com.fooddelivery.payment.UPIPayment;
import com.fooddelivery.services.DeliveryPartnerService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.services.UserService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class UserOrderMenu {
    private OrderService orderService;
    private UserService userService;
    private DeliveryPartnerService deliveryPartnerService;

    public UserOrderMenu(OrderService orderService, UserService userService, DeliveryPartnerService deliveryPartnerService) {
        this.orderService = orderService;
        this.userService = userService;
        this.deliveryPartnerService = deliveryPartnerService;
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
            System.out.println("5. Get Delivery Partner Information");
            System.out.println("6. Back to Main Customer Menu");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 6);

            switch (choice) {
                // place order
                case 1:
                    Address selectedAddress;
                    if (customer.getAddresses().isEmpty()) {
                        System.out.println("\nYou have no saved addresses. You must add one before placing an order.");
                        System.out.println("\n--- Add New Address ---");
                        String label = InputClass.readString(scanner, "Enter label (e.g., Home, Work): ");
                        String street = InputClass.readString(scanner, "Enter street name: ");
                        String city = InputClass.readString(scanner, "Enter city: ");
                        try {
                            selectedAddress = userService.addAddressToCustomer(customer, label, street, city);
                        } catch (Exception e) {
                            System.out.println("Error adding address: " + e.getMessage());
                            break;
                        }
                    } else {
                        System.out.println("\nSelect Delivery Address:");
                        for (int i = 0; i < customer.getAddresses().size(); i++) {
                            System.out.println((i + 1) + ". " + customer.getAddresses().get(i));
                        }
                        System.out.println((customer.getAddresses().size() + 1) + ". Add new Address");
                        int newLimit = customer.getAddresses().size() + 1;
                        int addrChoice = InputClass.readInt(scanner, "Choose option (1-" + newLimit + "): ", 1, newLimit);
                        if (addrChoice == newLimit) {
                            System.out.println("\n--- Add New Address ---");
                            String label = InputClass.readString(scanner, "Enter label (e.g., Home, Work): ");
                            String street = InputClass.readString(scanner, "Enter street name: ");
                            String city = InputClass.readString(scanner, "Enter city: ");
                            try {
                                selectedAddress = userService.addAddressToCustomer(customer, label, street, city);
                            } catch (Exception e) {
                                System.out.println("Error adding address: " + e.getMessage());
                                break;
                            }
                        } else {
                            selectedAddress = customer.getAddresses().get(addrChoice - 1);
                        }
                    }

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
                    orderService.placeOrderFlow(customer, paymentMode, selectedAddress);
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

                // contact detail of delivery partner
                case 5:
                    orderId = InputClass.readString(scanner, "Enter Order ID (e.g., ORD-1001): ");

                    try{
                        Order order = orderService.findOrderById(orderId);
                        deliveryPartnerService.getDeliveryPartnerDetails(order);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;

                // logout
                case 6:
                    System.out.println("Back to the Customer Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-6.");
            }
        }
    }
}
