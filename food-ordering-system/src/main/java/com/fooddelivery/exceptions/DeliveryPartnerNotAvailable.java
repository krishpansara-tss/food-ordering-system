package com.fooddelivery.exceptions;

public class DeliveryPartnerNotAvailable extends RuntimeException {
    public DeliveryPartnerNotAvailable(String message) {
        super(message);
    }
}
