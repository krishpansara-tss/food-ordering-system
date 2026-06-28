package com.fooddelivery.menu;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.model.CartItem;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Order;
import com.fooddelivery.services.DeliveryPartnerService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.List;
import java.util.Scanner;

public class DeliveryPartnerMenu {
    private OrderService orderService;
    private DeliveryPartnerService deliveryPartnerService;

    public DeliveryPartnerMenu(OrderService orderService, DeliveryPartnerService deliveryPartnerService) {
        this.orderService = orderService;
        this.deliveryPartnerService = deliveryPartnerService;
    }

    public void deliveryPartnerMenu(DeliveryPartner partner, Scanner scanner) {
        if (!(partner instanceof DeliveryPartner)) {
            System.out.println("Error: Logged in user is not a Delivery Partner.");
            return;
        }

        System.out.println("Welcome, " + partner.getUserName() + "!");

        while (true) {
            System.out.println("\n--- Delivery Partner Portal ---");
            System.out.println("1. Check Availability Status");
            System.out.println("2. Toggle Availability (Online/Offline)");
            System.out.println("3. View Current Assigned Order");
            System.out.println("4. View All Assigned Orders History");
            System.out.println("5. Update Current Order Status");
            System.out.println("6. Log out");
            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 6);

            switch (choice) {
                // availability status
                case 1:
                    System.out.println("\nYour current availability status is: "
                            + (partner.isAvailable() ? "AVAILABLE (ONLINE)" : "BUSY/OFFLINE"));
                    break;

                // toggle availability
                case 2:
                    partner.setAvailable(!partner.isAvailable());
                    System.out.println("\nAvailability status updated to: "
                            + (partner.isAvailable() ? "AVAILABLE (ONLINE)" : "BUSY/OFFLINE"));
                    break;

                // view current assigned order
                case 3:
                    Order currentOrder = partner.getCurrentOrder();
                    if (currentOrder == null) {
                        System.out.println("\nYou have no active order assigned at the moment.");
                    } else {
                        orderService.printOrderDetails(currentOrder);
                    }
                    break;

                // view all assigned orders history
                case 4:
                    deliveryPartnerService.displayAssignedOrder(partner);
                    break;

                // update current order status
                case 5:
                    Order activeOrder = partner.getCurrentOrder();
                    if (activeOrder == null) {
                        System.out.println("\nYou have no active order to update.");
                        break;
                    }

                    System.out.println("\nCurrent Order: " + activeOrder.getOrderId()
                            + "  |  Status: " + activeOrder.getOrderStatus());
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
                        orderService.updateOrderStatus(activeOrder.getOrderId(), newStatus);

                        if (newStatus == OrderStatus.DELIVERED) {
                            partner.setCurrentOrder(null);
                            System.out.println("Great work! You are now marked as AVAILABLE for new orders.");
                        }
                    } catch (Exception e) {
                        System.out.println("Failed to update status: " + e.getMessage());
                    }
                    break;

                // logout
                case 6:
                    System.out.println("Logging out Delivery Partner session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-6.");
            }
        }
    }


}
