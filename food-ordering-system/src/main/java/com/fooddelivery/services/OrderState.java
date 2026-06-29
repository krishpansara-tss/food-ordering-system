package com.fooddelivery.services;

import com.fooddelivery.model.Order;

public interface OrderState {
    void next(Order order);
    String getName();
}
