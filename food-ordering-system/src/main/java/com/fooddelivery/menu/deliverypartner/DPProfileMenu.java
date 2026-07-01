package com.fooddelivery.menu.deliverypartner;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.exceptions.DeliveryPartnerNotAvailable;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Order;
import com.fooddelivery.services.DeliveryPartnerService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class DPProfileMenu {
    private DeliveryPartnerService deliveryPartnerService;

    public DPProfileMenu(DeliveryPartnerService deliveryPartnerService) {
        this.deliveryPartnerService = deliveryPartnerService;
    }

    public void deliveryPartnerProfileMenu(DeliveryPartner partner, Scanner scanner) {
        System.out.println("Manage Your Profile From Here!");

        while (true) {
            System.out.println("\n--- Delivery Partner Portal ---");
            System.out.println("1. Check Availability Status");
            System.out.println("2. Toggle Availability (Online/Offline)");
            System.out.println("3. Get Your Statistic");
            System.out.println("4. Back to Main Delivery Partner Menu");

            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 4);

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

                // get your statistic
                case 3:
                    try {
                        deliveryPartnerService.displayDeliveryPartnerStatistic(partner);
                    } catch (DeliveryPartnerNotAvailable e) {
                        System.out.println("ERROR: " + e.getMessage());
                    }
                    break;

                // back
                case 4:
                    System.out.println("Back to Delivery Partner Main Menu...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-4.");
            }
        }
    }}
