package com.fooddelivery.menu;


import com.fooddelivery.menu.deliverypartner.DPOrderMenu;
import com.fooddelivery.menu.deliverypartner.DPProfileMenu;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.services.DeliveryPartnerService;
import com.fooddelivery.services.OrderService;
import com.fooddelivery.util.InputClass;

import java.util.Scanner;

public class DPMenu {
        private OrderService orderService;
        private DeliveryPartnerService deliveryPartnerService;

        public DPMenu(OrderService orderService, DeliveryPartnerService deliveryPartnerService) {
            this.orderService = orderService;
            this.deliveryPartnerService = deliveryPartnerService;
        }

        private DPOrderMenu dpOrderMenu= new DPOrderMenu(orderService, deliveryPartnerService);
        private DPProfileMenu dpProfileMenu = new DPProfileMenu(deliveryPartnerService);


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
                    // availability status
                    case 1:
                        dpProfileMenu.deliveryPartnerProfileMenu(partner, scanner);
                        break;

                    // toggle availability
                    case 2:
                        dpOrderMenu.deliveryPartnerOrderMenu(partner, scanner);
                        partner.setAvailable(!partner.isAvailable());

                    // logout
                    case 3:
                        System.out.println("Logging out Delivery Partner session...");
                        return;

                    default:
                        System.out.println("Invalid choice. Please select from 1-6.");
                }
            }
        }


    }
