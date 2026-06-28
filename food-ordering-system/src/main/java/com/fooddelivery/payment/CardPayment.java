package com.fooddelivery.payment;

import com.fooddelivery.interfaces.PaymentMode;

public class CardPayment implements PaymentMode {
    @Override
    public void pay(double amount) {
        System.out.println("Processing Card Payment of ₹" + amount);
        System.out.println("Payment Successful via Credit/Debit Card.");
    }

    @Override
    public String toString() {
        return "Card Payment";
    }
}
