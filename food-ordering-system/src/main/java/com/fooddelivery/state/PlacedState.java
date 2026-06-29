package com.fooddelivery.state;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.interfaces.OrderState;
import com.fooddelivery.model.Order;

public class PlacedState implements OrderState {
    @Override
    public void nextState(Order order) {

    }

    @Override
    public void currentState() {

    }

    @Override
    public OrderStatus getStatus() {
        return null;
    }
}
