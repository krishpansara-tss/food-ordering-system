package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

public abstract class User {
    private String userId;
    private String userName;
    private String phoneNumber;
    private String city;
    private String password;
    private UserType userType;

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
