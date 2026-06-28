package com.fooddelivery.model;

import com.fooddelivery.enums.UserType;

public class Admin extends User {
    private static long adminCount = 999;

    // Regular constructor — auto-increments ID (use this for normal admin creation)
    public Admin(String userName, String password, String phoneNumber, String city) {
        super("ADMN-" + (++adminCount), userName, password, phoneNumber, city, UserType.ADMIN);
    }

    // Static factory method — creates a seeded admin with a fixed ID (use only in LoadDummyData)
    public static Admin createDefaultAdmin(String fixedId, String userName, String password, String phoneNumber, String city) {
        Admin admin = new Admin(userName, password, phoneNumber, city);
        admin.userId = fixedId; // override the auto-generated ID (userId is protected in User)
        return admin;
    }
}
