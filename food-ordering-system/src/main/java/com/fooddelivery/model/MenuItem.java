package com.fooddelivery.model;


public class MenuItem {
    private static long itemCount = 1000;
    private String menuItemId;
    private String itemName;
    private double price;
    private boolean isVeg;

    public MenuItem(String itemName, double price, boolean isVeg) {
        this.menuItemId = "ITEM-" + (++itemCount);
        this.itemName = itemName;
        this.price = price;
        this.isVeg = isVeg;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean veg) {
        isVeg = veg;
    }

    public String getMenuItemId() {
        return menuItemId;
    }
}
