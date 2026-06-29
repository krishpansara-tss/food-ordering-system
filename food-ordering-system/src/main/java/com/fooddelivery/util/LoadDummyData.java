package com.fooddelivery.util;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.factory.RestaurantFactory;
import com.fooddelivery.factory.UserFactory;
import com.fooddelivery.model.Admin;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;

public class LoadDummyData {

    public static void seed(UserRepository userRepository, RestaurantRepository restaurantRepository) {

        Admin defaultAdmin = Admin.createDefaultAdmin("ADMIN-001", "Super Admin", "admin@123", "9000000000", "Ahmedabad");
        userRepository.addUser(defaultAdmin);

        User c1 = UserFactory.createuser(UserType.CUSTOMER, "Rushi",    "123",   "9876543210", "Rajkot");
        User c2 = UserFactory.createuser(UserType.CUSTOMER, "Deep",    "123",  "9876500001", "Surat");
        User c3 = UserFactory.createuser(UserType.CUSTOMER, "Het",   "123",  "9876500002", "Vadodara");
        User c4 = UserFactory.createuser(UserType.CUSTOMER, "Pooja",   "123",  "9876500003", "Ahmedabad");
        userRepository.addUser(c1);
        userRepository.addUser(c2);
        userRepository.addUser(c3);
        userRepository.addUser(c4);

        User dp1 = UserFactory.createuser(UserType.DELIVERY_PARTNER, "Nilesh", "123", "9988776600", "Rajkot");
        User dp2 = UserFactory.createuser(UserType.DELIVERY_PARTNER, "Jaydeep",   "123",  "9988776601", "Surat");
        User dp3 = UserFactory.createuser(UserType.DELIVERY_PARTNER, "Bharat",  "123", "9988776602", "Ahmedabad");
        userRepository.addUser(dp1);
        userRepository.addUser(dp2);
        userRepository.addUser(dp3);

        Restaurant r1 = RestaurantFactory.createRestaurant("Mavdi Chowk Khichdi House", "9000111001", "Rajkot");
        r1.addMenuItem(new MenuItem("Dal Khichdi",           60.0,  true));
        r1.addMenuItem(new MenuItem("Masala Khichdi",        75.0,  true));
        r1.addMenuItem(new MenuItem("Gujarati Thali",        180.0, true));
        r1.addMenuItem(new MenuItem("Methi Thepla",          40.0,  true));
        r1.addMenuItem(new MenuItem("Aamras Puri",           90.0,  true));
        r1.addMenuItem(new MenuItem("Chicken Curry",         160.0, false));
        restaurantRepository.addRestaurant(r1);

        Restaurant r2 = RestaurantFactory.createRestaurant("Surat Locha Corner",       "9000111002", "Surat");
        r2.addMenuItem(new MenuItem("Surat Locha",           50.0,  true));
        r2.addMenuItem(new MenuItem("Ghari Mithai",          120.0, true));
        r2.addMenuItem(new MenuItem("Undhiyu",               150.0, true));
        r2.addMenuItem(new MenuItem("Vada",             60.0,  true));
        r2.addMenuItem(new MenuItem("Surti Papdi Chaat",     70.0,  true));
        r2.addMenuItem(new MenuItem("Mutton Biryani",        220.0, false));
        restaurantRepository.addRestaurant(r2);

        Restaurant r3 = RestaurantFactory.createRestaurant("Ahmedabad Adda",   "9000111003", "Ahmedabad");
        r3.addMenuItem(new MenuItem("Sev Usal",              55.0,  true));
        r3.addMenuItem(new MenuItem("Dabeli",                30.0,  true));
        r3.addMenuItem(new MenuItem("Fafda Jalebi Combo",   80.0,  true));
        r3.addMenuItem(new MenuItem("Kachori",               25.0,  true));
        r3.addMenuItem(new MenuItem("Handvo",                65.0,  true));
        r3.addMenuItem(new MenuItem("Chicken Sandwich",      130.0, false));
        restaurantRepository.addRestaurant(r3);

        System.out.println("------------------------------------------");
        System.out.println("[System] Dummy data loaded successfully!");
        System.out.println("[System] Default Admin  → ID: ADMIN-001       | Password: admin@123");
        System.out.println("[System] Customers      → " + c1.getUserId() + " (123)  | "
                + c2.getUserId() + " (123)");
        System.out.println("[System]                → " + c3.getUserId() + " (123) | "
                + c4.getUserId() + " (123)");
        System.out.println("[System] Delivery       → " + dp1.getUserId() + " (123) | "
                + dp2.getUserId() + " (123) | " + dp3.getUserId() + " (123)");
        System.out.println("[System] Restaurants    → " + r1.getRestaurantId() + " (Rajkot) | "
                + r2.getRestaurantId() + " (Surat) | " + r3.getRestaurantId() + " (Ahmedabad)");
        System.out.println("------------------------------------------");
    }
}
