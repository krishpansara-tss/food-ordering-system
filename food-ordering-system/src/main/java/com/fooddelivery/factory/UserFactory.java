package com.fooddelivery.factory;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.exceptions.InvalidUserTypeException;
import com.fooddelivery.model.Admin;
import com.fooddelivery.model.Customer;
import com.fooddelivery.model.DeliveryPartner;
import com.fooddelivery.model.User;

public class UserFactory {

    public static User createuser(UserType userType, String name, String password, String phoneNumber, String city){
        switch (userType) {
            case CUSTOMER:
                return new Customer(name, password, phoneNumber, city);
            case DELIVERY_PARTNER:
                return new DeliveryPartner(name, password, phoneNumber, city);
            default:
                throw new InvalidUserTypeException("Unknown user type: " + userType);
        }
    }

    public static User createAdmin(String name, String password, String phoneNumber, String city){
        return new Admin(name, password, phoneNumber, city);
    }
}
