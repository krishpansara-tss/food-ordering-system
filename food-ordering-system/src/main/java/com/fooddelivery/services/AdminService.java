package com.fooddelivery.services;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.enums.UserType;
import com.fooddelivery.factory.UserFactory;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Order;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.UserRepository;

import java.util.Map;

public class AdminService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public AdminService(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public User addAdmin(String userName, String password, String phoneNumber, String city){
        User newAdmin = UserFactory.createAdmin(userName, password, phoneNumber, city);

        userRepository.addUser(newAdmin);

        return newAdmin;
    }

    public void displayAllAdmin(){
        Map<String, User> allUserMap = userRepository.getAllUserMap();

        if(allUserMap.isEmpty()){
            System.out.println("No user registered yet.. ");
            return;
        }

        boolean found = false;
        System.out.println("\n======================= GLOBAL USER DIRECTORY =======================");
        System.out.printf("| %-12s | %-18s | %-18s | %-12s |\n", "Account ID", "User Name", "Account Type", "City");
        System.out.println("+--------------+--------------------+--------------------+--------------+");

        for (User u : allUserMap.values()) {
            if(u.getUserType() == UserType.ADMIN){
                found = true;
                String accountType = u.getUserType().toString();

                System.out.printf("| %-12s | %-18s | %-18s | %-12s |\n",
                        u.getUserId(), u.getUserName(), accountType, u.getCity());
            }
        }

        if(!found){
            System.out.println("No admin found.");
            return;
        }

        System.out.println("=====================================================================");
    }

    public void displayAppStatistics() {
        Map<String,Order> orders = orderRepository.getOrderMap();
        Map<String, User> users = userRepository.getAllUserMap();

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
        System.out.printf("   - Total Revenue Earned           : ₹%.2f\n", totalGrossRevenue);
        System.out.printf("   - Discounts Paid Out             : ₹%.2f\n", totalDiscountsGiven);
        System.out.printf("   - Avg Order Value                : ₹%.2f\n", orders.isEmpty() ? 0 : (totalGrossRevenue / orders.size()));

        System.out.println("\n LOGISTICS & DISTRIBUTION:");
        System.out.println("   - Total Received Orders         : " + orders.size());
        System.out.println("   - Completed Deliveries          : " + completedDeliveries);
        System.out.println("   - Active Orders                 : " + activeOrders);

        System.out.println("\n NETWORK DEMOGRAPHICS:");
        System.out.println("   - Total Registered Customers    : " + customerCount);
        System.out.println("   - Onboarded Delivery Partners   : " + partnerCount);
        System.out.println("   - Available Delivery Partner    : " + availableDrivers);
        System.out.println("====================================================");
    }

    public void displayRestaurantStatistics(String restaurantId) {
        Map<String,Order> orders = orderRepository.getOrderMap();

        double totalGrossRevenue = 0;
        double totalDiscountsGiven = 0;
        int completedDeliveries = 0;
        int activeOrders = 0;
        int orderCount = 0;

        for (Order o : orders.values()) {
            if(o.getRestaurantId().equals(restaurantId)){
                orderCount++;
                totalGrossRevenue += o.getFinalAmount();
                totalDiscountsGiven += o.getAppliedDiscount();

                if (o.getOrderStatus() == OrderStatus.DELIVERED) {
                    completedDeliveries++;
                } else {
                    activeOrders++;
                }
            }
        }

        if(orderCount == 0){
            System.out.println("Haven't received orders yet!!!");
            return;
        }

        System.out.println("\n====================================================");
        System.out.println("             RESTAURANT PERFORMANCE METRICS          ");
        System.out.println("====================================================");
        System.out.println(" FINANCIAL SUMMARY:");
        System.out.printf("   - Total Revenue Earned          : ₹%.2f\n", totalGrossRevenue);
        System.out.printf("   - Discounts Paid Out            : ₹%.2f\n", totalDiscountsGiven);
        System.out.printf("   - Avg Order Value               : ₹%.2f\n", orderCount == 0 ? 0 : (totalGrossRevenue / orderCount));

        System.out.println("\n LOGISTICS & DISTRIBUTION:");
        System.out.println("   - Total Received Orders        : " + orderCount);
        System.out.println("   - Completed Deliveries         : " + completedDeliveries);
        System.out.println("   - Active Orders                : " + activeOrders);

        System.out.println("====================================================");
    }
}
