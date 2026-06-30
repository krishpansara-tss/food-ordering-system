package com.fooddelivery.menu.deliverypartner;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.exceptions.DeliveryPartnerNotAvailable;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Order;
import com.fooddelivery.services.DeliveryPartnerService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class DPOrderMenu {
    private OrderService orderService;
    private DeliveryPartnerService deliveryPartnerService;

    public DPOrderMenu(OrderService orderService, DeliveryPartnerService deliveryPartnerService) {
        this.orderService = orderService;
        this.deliveryPartnerService = deliveryPartnerService;
    }

    public void deliveryPartnerOrderMenu(DeliveryPartner partner, Scanner scanner) {
        System.out.println("Manage Your Orders From Here!");

        while (true) {
            System.out.println("\n--- Delivery Partner Portal ---");
            System.out.println("1. View Current Assigned Order");
            System.out.println("2. View All Assigned Orders History");
            System.out.println("3. Update Current Order Status");
            System.out.println("4. Log out");
            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 4);

            switch (choice) {
                // view current assigned order
                case 1:
                    Order currentOrder = partner.getCurrentOrder();
                    if (currentOrder == null) {
                        System.out.println("\nYou have no active order assigned at the moment.");
                    } else {
                        orderService.printOrderDetails(currentOrder);
                    }
                    break;

                // view all assigned orders history
                case 2:
                    deliveryPartnerService.displayAssignedOrder(partner);
                    break;

                // update current order status
                case 3:
                    Order activeOrder = partner.getCurrentOrder();
                    if (activeOrder == null) {
                        System.out.println("\nYou have no active order to update.");
                        break;
                    }

                    OrderStatusType current = activeOrder.getOrderStatus().getStatus();
                    System.out.println("\nCurrent Order: " + activeOrder.getOrderId()
                            + "  |  Status: " + current);

                    if (current == OrderStatusType.APPROVED_BY_RESTAURANT) {
                        System.out.println("Current Order Status: " + current);
                        System.out.println("You don't have access to update current status");
                        break;
                    }

                    if (current == OrderStatusType.DELIVERED) {
                        System.out.println("Order is already delivered.");
                        break;
                    }
                    OrderStatusType next = null;
                    if (current == OrderStatusType.READY_FOR_DELIVERY) next = OrderStatusType.OUT_FOR_DELIVERY;
                    else if (current == OrderStatusType.OUT_FOR_DELIVERY) next = OrderStatusType.DELIVERED;

                    boolean changeStatus = InputClass.readBoolean(scanner, "Do you want to change order status to " + next + "? (true/false): ");

                    if (changeStatus) {
                        try {
                            orderService.updateOrderStatus(activeOrder.getOrderId());

                            if (activeOrder.getOrderStatus().getStatus() == OrderStatusType.DELIVERED) {
                                partner.setCurrentOrder(null);
                                System.out.println("Great work! You are now marked as AVAILABLE for new orders.");
                            }
                        } catch (Exception e) {
                            System.out.println("Failed to update status: " + e.getMessage());
                        }
                    } else {
                        System.out.println("No changes are made to the Order Status");
                    }
                    break;

                // Back
                case 4:
                    System.out.println("Back to Delivery Partner Main Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-4.");
            }
        }
    }
}
