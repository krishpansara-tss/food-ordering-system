package com.fooddelivery.payment;

import com.fooddelivery.interfaces.PaymentMode;

public class CODPayment implements PaymentMode {
    @Override
    public void pay(double amount) {
        System.out.println("Cash on Delivery (COD) selected.");
        System.out.println("Please keep ₹" + amount + " cash ready upon delivery.");
    }

    public void paymentDoneOnCOD(double amount){
        System.out.println("Payment of ₹"+ amount +" is Successfully done on COD.");
    }

    @Override
    public String toString() {
        return "Cash on Delivery (COD)";
    }
}
