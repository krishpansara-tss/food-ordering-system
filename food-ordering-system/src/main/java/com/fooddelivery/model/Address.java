package com.fooddelivery.model;

public class Address {
    private String label;
    private String street;
    private String city;

    public Address(String label, String street, String city) {
        this.label = label;
        this.street = street;
        this.city = city;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return label + " (" + street + ", " + city + ")";
    }
}
