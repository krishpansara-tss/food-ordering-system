package com.fooddelivery.state;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.interfaces.OrderStatus;
import com.fooddelivery.model.Order;

public class ReadyForDeliveryState implements OrderStatus {
    @Override
    public void nextState(Order order) {
        order.setOrderStatus(new OutForDeliveryState());
    }

    @Override
    public void currentState() {
        System.out.println("Order is prepared and now ready to dispatch.");
    }

    @Override
    public OrderStatusType getStatus() {
        return OrderStatusType.READY_FOR_DELIVERY;
    }

    @Override
    public String toString() {
        return "PREPARED";
    }
}
