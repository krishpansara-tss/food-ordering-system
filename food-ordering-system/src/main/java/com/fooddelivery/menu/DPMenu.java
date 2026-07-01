package com.fooddelivery.menu;


import com.fooddelivery.menu.deliverypartner.DPOrderMenu;
import com.fooddelivery.menu.deliverypartner.DPProfileMenu;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.services.DeliveryPartnerService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class DPMenu {
    private DPOrderMenu dpOrderMenu;
    private DPProfileMenu dpProfileMenu;

    public DPMenu(OrderService orderService, DeliveryPartnerService deliveryPartnerService) {
        this.dpOrderMenu = new DPOrderMenu(orderService, deliveryPartnerService);
        this.dpProfileMenu = new DPProfileMenu(deliveryPartnerService);
    }


    public void deliveryPartnerMenu(DeliveryPartner partner, Scanner scanner) {
        if (!(partner instanceof DeliveryPartner)) {
            System.out.println("Error: Logged in user is not a Delivery Partner.");
            return;
        }

        System.out.println("Welcome, " + partner.getUserName() + "!");

        while (true) {
            System.out.println("\n--- Delivery Partner Portal ---");
            System.out.println("1. Manage your Profile");
            System.out.println("2. Manage Order Assigned to You");
            System.out.println("3. Log out");
            int choice = InputClass.readInt(scanner, "Please enter your choice: ", 1, 3);

            switch (choice) {
                // manage profile
                case 1:
                    dpProfileMenu.deliveryPartnerProfileMenu(partner, scanner);
                    break;

                // manage orders
                case 2:
                    dpOrderMenu.deliveryPartnerOrderMenu(partner, scanner);
                    break;

                // logout
                case 3:
                    partner = null;
                    System.out.println("Logging out Delivery Partner session...");
                    return;

                default:
                    System.out.println("Invalid choice. Please select from 1-3.");
            }
        }
    }
}
