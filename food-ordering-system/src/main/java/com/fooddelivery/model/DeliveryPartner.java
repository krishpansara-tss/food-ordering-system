package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class DeliveryPartner extends User {
    private static long deliveryPartnerCount = 1000;
    private boolean isAvailable;
    private Order currentOrder = null;
    private List<Order> assignedOrderList = new ArrayList<>();
    private double earning;

    public DeliveryPartner(String userName, String password, String phoneNumber, String city) {
        super("DELV-" + (++deliveryPartnerCount), userName, password, phoneNumber, city, UserType.DELIVERY_PARTNER);
        this.earning = 0;
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public List<Order> getAssignedOrderList() {
        return assignedOrderList;
    }

    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }
}
