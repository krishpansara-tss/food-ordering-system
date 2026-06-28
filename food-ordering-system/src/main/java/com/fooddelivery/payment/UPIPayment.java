package com.fooddelivery.payment;

import com.fooddelivery.interfaces.PaymentMode;

public class UPIPayment implements PaymentMode {
    @Override
    public void pay(double amount) {
        System.out.println("Processing UPI Payment of ₹" + amount);
        System.out.println("Payment Successful via UPI App.");
    }

    @Override
    public String toString() {
        return "UPI Payment";
    }
}
