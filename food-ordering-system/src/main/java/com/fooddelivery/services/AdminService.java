package com.fooddelivery.services;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.factory.UserFactory;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.UserRepository;

import java.util.List;
import java.util.Map;

public class AdminService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public AdminService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public void addAdmin(String userName, String password, String phoneNumber, String city){
        User newAdmin = UserFactory.createAdmin(userName, password, phoneNumber, city);

        userRepository.addUser(newAdmin);

    }

    public void displayAppStatistics() {
        Map<String,Order> orders = orderRepository.getOrderMap();
        Map<String, User> users = userRepository.getAllUserMap();

        // 1. Calculate Financial Statistics
        double totalGrossRevenue = 0;
        double totalDiscountsGiven = 0;
        int completedDeliveries = 0;
        int activeOrders = 0;

        for (Order o : orders.values()) {
            totalGrossRevenue += o.getFinalAmount();
            totalDiscountsGiven += o.getAppliedDiscount();

            if (o.getOrderStatus() == OrderStatus.DELIVERED) {
                completedDeliveries++;
            } else {
                activeOrders++;
            }
        }

        int customerCount = 0;
        int partnerCount = 0;
        int availableDrivers = 0;

        for (User u : users.values()) {
            if (u instanceof Customer) {
                customerCount++;
            } else if (u instanceof DeliveryPartner) {
                partnerCount++;
                if (((DeliveryPartner) u).isAvailable()) {
                    availableDrivers++;
                }
            }
        }

        System.out.println("\n====================================================");
        System.out.println("             GLOBAL APP OPERATIONS METRICS          ");
        System.out.println("====================================================");
        System.out.println(" FINANCIAL SUMMARY:");
        System.out.printf("   - Total Revenue Earned     : ₹%.2f\n", totalGrossRevenue);
        System.out.printf("   - Marketing Subsidies/Discounts Paid Out : ₹%.2f\n", totalDiscountsGiven);
        System.out.printf("   - Avg Order Ticket Value   : ₹%.2f\n", orders.isEmpty() ? 0 : (totalGrossRevenue / orders.size()));

        System.out.println("\n LOGISTICS & DISTRIBUTION:");
        System.out.println("   - Total Orders Logged        : " + orders.size());
        System.out.println("   - Completed Deliveries       : " + completedDeliveries);
        System.out.println("   - Active Processing Runs     : " + activeOrders);

        System.out.println("\n NETWORK DEMOGRAPHICS:");
        System.out.println("   - Total Registered Customers : " + customerCount);
        System.out.println("   - Onboarded Fleet Partners   : " + partnerCount);
        System.out.println("   - Active Fleet Fleet Ready   : " + availableDrivers);
        System.out.println("====================================================");
    }
}
