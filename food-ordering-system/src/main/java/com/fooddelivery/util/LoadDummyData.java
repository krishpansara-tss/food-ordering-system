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

        // ─────────────────────────────────────────────
        // DEFAULT SUPER ADMIN (fixed ID)
        // ─────────────────────────────────────────────
        Admin defaultAdmin = Admin.createDefaultAdmin("ADMIN-001", "Super Admin", "admin@123", "9000000000", "Ahmedabad");
        userRepository.addUser(defaultAdmin);

        // ─────────────────────────────────────────────
        // CUSTOMERS  (Gujarat names & cities)
        // ─────────────────────────────────────────────
        User c1 = UserFactory.createuser(UserType.CUSTOMER, "Ravi Patel",    "ravi@123",   "9876543210", "Ahmedabad");
        User c2 = UserFactory.createuser(UserType.CUSTOMER, "Mital Shah",    "mital@123",  "9876500001", "Surat");
        User c3 = UserFactory.createuser(UserType.CUSTOMER, "Dhruv Desai",   "dhruv@123",  "9876500002", "Vadodara");
        User c4 = UserFactory.createuser(UserType.CUSTOMER, "Pooja Mehta",   "pooja@123",  "9876500003", "Ahmedabad");
        userRepository.addUser(c1);
        userRepository.addUser(c2);
        userRepository.addUser(c3);
        userRepository.addUser(c4);

        // ─────────────────────────────────────────────
        // DELIVERY PARTNERS (Gujarat names & cities)
        // ─────────────────────────────────────────────
        User dp1 = UserFactory.createuser(UserType.DELIVERY_PARTNER, "Nilesh Solanki", "nilesh@123", "9988776600", "Ahmedabad");
        User dp2 = UserFactory.createuser(UserType.DELIVERY_PARTNER, "Kiran Thakor",   "kiran@123",  "9988776601", "Surat");
        User dp3 = UserFactory.createuser(UserType.DELIVERY_PARTNER, "Bharat Vasava",  "bharat@123", "9988776602", "Vadodara");
        userRepository.addUser(dp1);
        userRepository.addUser(dp2);
        userRepository.addUser(dp3);

        // ─────────────────────────────────────────────
        // RESTAURANTS + MENU ITEMS
        // ─────────────────────────────────────────────

        // --- Restaurant 1: Ahmedabad ---
        Restaurant r1 = RestaurantFactory.createRestaurant("Manek Chowk Khichdi House", "9000111001", "Ahmedabad");
        r1.addMenuItem(new MenuItem("Dal Khichdi",           60.0,  true));
        r1.addMenuItem(new MenuItem("Masala Khichdi",        75.0,  true));
        r1.addMenuItem(new MenuItem("Gujarati Thali",        180.0, true));
        r1.addMenuItem(new MenuItem("Methi Thepla",          40.0,  true));
        r1.addMenuItem(new MenuItem("Aamras Puri",           90.0,  true));
        r1.addMenuItem(new MenuItem("Chicken Curry",         160.0, false));
        restaurantRepository.addRestaurant(r1);

        // --- Restaurant 2: Surat ---
        Restaurant r2 = RestaurantFactory.createRestaurant("Surat Locha Corner",       "9000111002", "Surat");
        r2.addMenuItem(new MenuItem("Surat Locha",           50.0,  true));
        r2.addMenuItem(new MenuItem("Ghari Mithai",          120.0, true));
        r2.addMenuItem(new MenuItem("Undhiyu",               150.0, true));
        r2.addMenuItem(new MenuItem("Ponk Vada",             60.0,  true));
        r2.addMenuItem(new MenuItem("Surti Papdi Chaat",     70.0,  true));
        r2.addMenuItem(new MenuItem("Mutton Biryani",        220.0, false));
        restaurantRepository.addRestaurant(r2);

        // --- Restaurant 3: Vadodara ---
        Restaurant r3 = RestaurantFactory.createRestaurant("Vadodara Sev Usal Adda",   "9000111003", "Vadodara");
        r3.addMenuItem(new MenuItem("Sev Usal",              55.0,  true));
        r3.addMenuItem(new MenuItem("Dabeli",                30.0,  true));
        r3.addMenuItem(new MenuItem("Fafda Jalebi Combo",   80.0,  true));
        r3.addMenuItem(new MenuItem("Kachori",               25.0,  true));
        r3.addMenuItem(new MenuItem("Handvo",                65.0,  true));
        r3.addMenuItem(new MenuItem("Chicken Sandwich",      130.0, false));
        restaurantRepository.addRestaurant(r3);

        System.out.println("─────────────────────────────────────────────────");
        System.out.println("[System] Dummy data loaded successfully!");
        System.out.println("[System] Default Admin  → ID: ADMIN-001       | Password: admin@123");
        System.out.println("[System] Customers      → " + c1.getUserId() + " (ravi@123)  | "
                + c2.getUserId() + " (mital@123)");
        System.out.println("[System]                → " + c3.getUserId() + " (dhruv@123) | "
                + c4.getUserId() + " (pooja@123)");
        System.out.println("[System] Delivery       → " + dp1.getUserId() + " (nilesh@123) | "
                + dp2.getUserId() + " (kiran@123) | " + dp3.getUserId() + " (bharat@123)");
        System.out.println("[System] Restaurants    → " + r1.getRestaurantId() + " (Ahmedabad) | "
                + r2.getRestaurantId() + " (Surat) | " + r3.getRestaurantId() + " (Vadodara)");
        System.out.println("─────────────────────────────────────────────────");
    }
}
