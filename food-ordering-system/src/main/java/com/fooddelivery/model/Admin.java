package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

public class Admin extends User {
    private static long adminCount = 999;

    public Admin(String userName, String password, String phoneNumber, String city, UserType userType) {
        super("ADMN-" + (++adminCount), userName, password, phoneNumber, city, userType);
    }
}
