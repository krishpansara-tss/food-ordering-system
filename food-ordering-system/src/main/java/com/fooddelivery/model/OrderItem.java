package com.fooddelivery.model;

public class OrderItem {
    private MenuItem menuItem = null;
    private int quantity;
    private double priceAtPurchase;
    private String menuItemId;
    private String menuItemName;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.priceAtPurchase = menuItem.getPrice();
        this.menuItemName = menuItem.getItemName();
        this.menuItemId = menuItem.getMenuItemId();
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }


    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }
}
