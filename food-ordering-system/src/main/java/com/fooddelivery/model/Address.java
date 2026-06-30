package com.fooddelivery.model;

public class Address {
    private String city;
    private String state;
    private String pincode;
    private String country;

    public Address(String city, String state, String pincode, String country) {
        this.city = city;
        this.state = state;
        this.pincode = pincode;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }

    public String getCountry() {
        return country;
    }
}
