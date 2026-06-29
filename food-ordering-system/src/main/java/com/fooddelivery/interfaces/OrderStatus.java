package com.fooddelivery.interfaces;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.model.Order;

public interface OrderStatus {
    void nextState(Order order);
    void currentState();
    OrderStatusType getStatus();
}
