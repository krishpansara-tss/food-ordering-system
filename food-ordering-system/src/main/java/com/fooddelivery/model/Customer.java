package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    private static long customerCount = 1000;
    private List<Order> orderHistory = new ArrayList<>();
    private Cart cart = new Cart();
    private List<Address> addresses = new ArrayList<>();

    public Customer(String userName, String password, String phoneNumber, String city) {
        super("CUST-" + (++customerCount), userName, password, phoneNumber, city, UserType.CUSTOMER);
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
