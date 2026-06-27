package com.fooddelivery.model;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.interfaces.PaymentMode;

public class Order {
    private static long orderCount = 1000;
    private String orderId;
    private Customer customer;
    private String restaurantId;
    private String restaurantName;
    private DeliveryPartner assingedDeliveryPartner;
    private Cart listOfItem;
    private double totalAmount;
    private double appliedDiscount;
    private double finalAmount;
    private PaymentMode paymentMode;    // rethink
    private OrderStatus orderStatus;

    public Order(Customer customer, String restaurantId, String restaurantName, DeliveryPartner assingedDeliveryPartner, Cart listOfItem, double totalAmount, double appliedDiscount, double finalAmount) {
        this.orderId =  ("ORD-") + (++orderCount);
        this.customer = customer;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.assingedDeliveryPartner = assingedDeliveryPartner;
        this.listOfItem = listOfItem;
        this.totalAmount = totalAmount;
        this.appliedDiscount = appliedDiscount;
        this.finalAmount = finalAmount;
        this.orderStatus = OrderStatus.PREPARING;
    }

    public String getOrderId() {
        return orderId;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public DeliveryPartner getAssingedDeliveryPartner() {
        return assingedDeliveryPartner;
    }

    public void setAssignedDeliveryPartner(DeliveryPartner assingedDeliveryPartner) {
        this.assingedDeliveryPartner = assingedDeliveryPartner;
    }

    public Cart getListOfItem() {
        return listOfItem;
    }

    public void setListOfItem(Cart listOfItem) {
        this.listOfItem = listOfItem;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(double appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }


    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
