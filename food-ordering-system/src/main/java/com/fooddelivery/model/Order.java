package com.fooddelivery.model;

import com.fooddelivery.enums.OrderStatus;
import com.fooddelivery.interfaces.PaymentMode;

import java.time.LocalDateTime;
import java.util.Date;

public class Order {
    private static long orderCount = 1000;
    private String orderId = null;
    private LocalDateTime orderDate;
    private Customer customer = null;
    private String restaurantId;
    private String restaurantName;
    private DeliveryPartner assignedDeliveryPartner = null;
    private Cart listOfItem = null;
    private double totalAmount;
    private double appliedDiscount;
    private double finalAmount;
    private PaymentMode paymentMode = null;
    private OrderStatus orderStatus = null;

    private Order(Builder builder){
        this.orderDate = LocalDateTime.now();
        this.orderId = "ORD-" + (++orderCount);
        this.customer = builder.customer;
        this.restaurantId = builder.restaurantId;
        this.restaurantName = builder.restaurantName;
        this.assignedDeliveryPartner = builder.assignedDeliveryPartner;
        this.listOfItem = builder.listOfItem;
        this.totalAmount = builder.totalAmount;
        this.appliedDiscount = builder.appliedDiscount;
        this.finalAmount = builder.finalAmount;
        this.paymentMode = builder.paymentMode;
        this.orderStatus = builder.orderStatus != null
                ? builder.orderStatus
                : OrderStatus.PREPARING;
    }

    public static class Builder{

        private Customer customer;
        private String restaurantId;
        private String restaurantName;
        private DeliveryPartner assignedDeliveryPartner;
        private Cart listOfItem;
        private double totalAmount;
        private double appliedDiscount;
        private double finalAmount;
        private PaymentMode paymentMode;
        private OrderStatus orderStatus;

        public Builder customer(Customer customer){
            this.customer = customer;
            return this;
        }
        public Builder restaurantId(String restaurantId){
            this.restaurantId = restaurantId;
            return this;
        }
        public Builder restaurantName(String restaurantName){
            this.restaurantName = restaurantName;
            return this;
        }
        public Builder assignedDeliveryPartner(DeliveryPartner assignedDeliveryPartner){
            this.assignedDeliveryPartner = assignedDeliveryPartner;
            return this;
        }
        public Builder listOfItem(Cart listOfItem){
            this.listOfItem = listOfItem;
            return this;
        }
        public Builder totalAmount(double totalAmount){
            this.totalAmount = totalAmount;
            return this;
        }
        public Builder appliedDiscount(double appliedDiscount){
            this.appliedDiscount = appliedDiscount;
            return this;
        }
        public Builder finalAmount(double finalAmount){
            this.finalAmount = finalAmount;
            return this;
        }
        public Builder paymentMode(PaymentMode paymentMode){
            this.paymentMode = paymentMode;
            return this;
        }
        public Builder orderStatus(OrderStatus orderStatus){
            this.orderStatus = orderStatus;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
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
        return assignedDeliveryPartner;
    }

    public void setAssignedDeliveryPartner(DeliveryPartner assignedDeliveryPartner) {
        this.assignedDeliveryPartner = assignedDeliveryPartner;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
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

    public double getAppliedDiscount() {
        return appliedDiscount;
    }

    public double getFinalAmount() {
        return finalAmount;
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
