package com.fooddelivery.state;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.interfaces.OrderStatus;
import com.fooddelivery.model.Order;

public class OutForDeliveryState implements OrderStatus {
    @Override
    public void nextState(Order order) {
        order.setOrderStatus(new DeliveredState());
    }

    @Override
    public void currentState() {
        System.out.println("Order is out for delivery.");
    }

    @Override
    public OrderStatusType getStatus() {
        return OrderStatusType.OUT_FOR_DELIVERY;
    }

    @Override
    public String toString() {
        return "OUT_FOR_DELIVERY";
    }
}
