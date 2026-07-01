package com.fooddelivery.services;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.exceptions.DeliveryPartnerNotAvailable;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.Order;

import java.util.List;

public class DeliveryPartnerService {

    public void displayAssignedOrder(DeliveryPartner deliveryPartner){
        List<Order> allOrders = deliveryPartner.getAssignedOrderList();
        if (allOrders == null || allOrders.isEmpty()) {
            System.out.println("\nYou have no order history yet.");
        } else {
            System.out.println("\n================ YOUR ORDER HISTORY ================");
            for (Order o : allOrders) {
                System.out.println("Order ID : " + o.getOrderId()
                        + "  |  Restaurant: " + o.getRestaurantName()
                        + "  |  Status: " + o.getOrderStatus()
                        + "  |  Amount: ₹" + o.getFinalAmount());
            }
            System.out.println("====================================================");
        }
    }

    public void displayDeliveryPartnerStatistic(DeliveryPartner deliveryPartner){
        if(deliveryPartner == null){
            throw new DeliveryPartnerNotAvailable("Delivery Partner not found");
        }

        System.out.println("\n================ YOUR Statistic ================");
        System.out.println("Delivery Partner ID     : " + deliveryPartner.getUserId());
        System.out.println("Delivery Name           : " + deliveryPartner.getUserName());
        System.out.println("Total Assigned Orders   : " + deliveryPartner.getAssignedOrderList().size());
        System.out.println("Current Active Order ID : " + deliveryPartner.getCurrentOrder().getOrderId());
        System.out.println("Total Earnings          : " + deliveryPartner.getEarning());
        System.out.println("Current Status          : " + (deliveryPartner.isAvailable() ? "AVAILABLE" : "BUSY"));
    }

    public void getDeliveryPartnerDetails(Order order){
        DeliveryPartner deliveryPartner = order.getAssignedDeliveryPartner();
        if(deliveryPartner == null){
            throw new DeliveryPartnerNotAvailable("Delivery Partner Not Found");
        }
        if(order.getOrderStatus().getStatus() == OrderStatusType.DELIVERED){
            System.out.println("Your order has been already delivered");
            return;
        }

        System.out.println("------ Contact Detail ------");
        System.out.println("Delivery Partner ID   :" + deliveryPartner.getUserId());
        System.out.println("Restaurant Name : " + deliveryPartner.getUserName());
        System.out.println("Contact Number  : " + deliveryPartner.getPhoneNumber());
        System.out.println("-----------------------------");
    }
}
