package com.fooddelivery.state;

import com.fooddelivery.enums.OrderStatusType;
import com.fooddelivery.interfaces.OrderStatus;

public class OrderStateBuilder {
    public static OrderStatus build(OrderStatusType type) {
        if (type == null) {
            return new PlacedState();
        }
        switch (type) {
            case APPROVED_BY_RESTAURANT:
                return new PlacedState();
            case READY_FOR_DELIVERY:
                return new ReadyForDeliveryState();
            case OUT_FOR_DELIVERY:
                return new OutForDeliveryState();
            case DELIVERED:
                return new DeliveredState();
            default:
                throw new IllegalArgumentException("Unknown order status type: " + type);
        }
    }
}
