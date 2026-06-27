package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

public class DeliveryPartner extends User {
    private static long deliveryPartnerCount = 1000;
    private boolean isAvailable;

    public DeliveryPartner(String userName, String password, String phoneNumber, String city) {
        super("CUST-" + (++deliveryPartnerCount), userName, password, phoneNumber, city, UserType.DELIVERY_PARTNER);
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
