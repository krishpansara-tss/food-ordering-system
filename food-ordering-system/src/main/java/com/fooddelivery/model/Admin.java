package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

public class Admin extends User {
    private static long adminCount = 1000;
    private String tempId;

    public Admin(String userName, String password, String phoneNumber, String city) {
        super("CUST-" + (++adminCount), userName, password, phoneNumber, city, UserType.ADMIN);
    }
}
