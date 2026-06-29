package com.fooddelivery.state;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.interfaces.OrderStatus;
import com.fooddelivery.model.Order;

public class DeliveredState implements OrderStatus {
    @Override
    public void nextState(Order order) {
        throw new IllegalArgumentException("Order is already delivered. No further state transitions.");
    }

    @Override
    public void currentState() {
        System.out.println("Order is successfully delivered.");
    }

    @Override
    public OrderStatusType getStatus() {
        return OrderStatusType.DELIVERED;
    }

    @Override
    public String toString() {
        return "DELIVERED";
    }
}
