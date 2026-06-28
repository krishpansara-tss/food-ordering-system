package com.fooddelivery.services;

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
}
