package com.fooddelivery.repository;

import com.fooddelivery.model.Order;

import java.util.HashMap;
import java.util.Map;

public class OrderRepository {
    // userId, Order
    private Map<String, Order> orderMap = new HashMap<>();

    public void addOrder(Order order){
        orderMap.put(order.getOrderId(), order);
    }

    public Map<String, Order> getOrderMap() {
        return orderMap;
    }

    public Order findOrderById(String orderId){
        return orderMap.get(orderId);
    }
}
