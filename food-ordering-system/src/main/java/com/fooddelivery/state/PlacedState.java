package com.fooddelivery.state;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.model.Order;
import com.fooddelivery.services.OrderState;

public class PlacedState implements OrderState {
    @Override
    public void next(Order order) {
//        order.setOrderStatus(new OutForDeliveryState());
    }

    @Override
    public String getName() {
        return "PLACED";
    }
}
