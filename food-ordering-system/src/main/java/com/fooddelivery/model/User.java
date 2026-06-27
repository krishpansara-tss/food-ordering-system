package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

public abstract class User {
    protected String userId;
    protected String userName;
    protected String phoneNumber;
    protected String city;
    protected String password;
    protected UserType userType;

    public User(String userId, String userName, String password, String phoneNumber, String city, UserType userType) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCity() {
        return city;
    }
}
