package com.fooddelivery.interfaces;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.model.Order;

public interface OrderState {
    void nextState(Order order);
    void currentState();
    OrderStatus getStatus();
}
