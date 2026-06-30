package com.fooddelivery.util;

import com.fooddelivery.enums.CuisineType;
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

        User defaultAdmin = UserFactory.createAdmin(UserType.SUPER_ADMIN,"Super Admin", "admin@123", "9000000000", "Ahmedabad");
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

        Restaurant r1 = RestaurantFactory.createRestaurant("Mavdi Chowk Khichdi House","123", "9000111001", "Rajkot");
        r1.addMenuItem(new MenuItem("Dal Khichdi",           60.0,  true, CuisineType.INDIAN));
        r1.addMenuItem(new MenuItem("Masala Khichdi",        75.0,  true, CuisineType.INDIAN));
        r1.addMenuItem(new MenuItem("Gujarati Thali",        180.0, true, CuisineType.GUJARATI));
        r1.addMenuItem(new MenuItem("Methi Thepla",          40.0,  true, CuisineType.GUJARATI));
        r1.addMenuItem(new MenuItem("Aamras Puri",           90.0,  true, CuisineType.GUJARATI));
        r1.addMenuItem(new MenuItem("Chicken Curry",         160.0, false, CuisineType.INDIAN));
        r1.addMenuItem(new MenuItem("Veg Noodles",           90.0,  true, CuisineType.CHINESE));
        r1.addMenuItem(new MenuItem("Gobi Manchurian",       100.0, true, CuisineType.CHINESE));
        restaurantRepository.addRestaurant(r1);

        Restaurant r2 = RestaurantFactory.createRestaurant("Surat Locha Corner",   "123",    "9000111002", "Surat");
        r2.addMenuItem(new MenuItem("Surat Locha",           50.0,  true, CuisineType.STREET_FOOD));
        r2.addMenuItem(new MenuItem("Ghari Mithai",          120.0, true, CuisineType.GUJARATI));
        r2.addMenuItem(new MenuItem("Undhiyu",               150.0, true, CuisineType.GUJARATI));
        r2.addMenuItem(new MenuItem("Vada",                  60.0,  true, CuisineType.STREET_FOOD));
        r2.addMenuItem(new MenuItem("Surti Papdi Chaat",     70.0,  true, CuisineType.STREET_FOOD));
        r2.addMenuItem(new MenuItem("Mutton Biryani",        220.0, false, CuisineType.INDIAN));
        r2.addMenuItem(new MenuItem("Margherita Pizza",      150.0, true, CuisineType.ITALIAN));
        r2.addMenuItem(new MenuItem("Pasta Primavera",       160.0, true, CuisineType.ITALIAN));
        restaurantRepository.addRestaurant(r2);

        Restaurant r3 = RestaurantFactory.createRestaurant("Ahmedabad Adda","123","9000111003", "Ahmedabad");
        r3.addMenuItem(new MenuItem("Sev Usal",              55.0,  true, CuisineType.STREET_FOOD));
        r3.addMenuItem(new MenuItem("Dabeli",                30.0,  true, CuisineType.STREET_FOOD));
        r3.addMenuItem(new MenuItem("Fafda Jalebi Combo",    80.0,  true, CuisineType.GUJARATI));
        r3.addMenuItem(new MenuItem("Kachori",               25.0,  true, CuisineType.STREET_FOOD));
        r3.addMenuItem(new MenuItem("Handvo",                65.0,  true, CuisineType.GUJARATI));
        r3.addMenuItem(new MenuItem("Chicken Sandwich",      130.0, false, CuisineType.FAST_FOOD));
        r3.addMenuItem(new MenuItem("Margherita Pizza",      150.0, true, CuisineType.ITALIAN));
        r3.addMenuItem(new MenuItem("Veg Noodles",           90.0,  true, CuisineType.CHINESE));
        restaurantRepository.addRestaurant(r3);

        System.out.println("------------------------------------------");
        System.out.println("[System] Dummy data loaded successfully!");

        System.out.println("[System] Default Admin  → ID: "
                + defaultAdmin.getUserId()
                + " | Name: " + defaultAdmin.getUserName()
                + " | Password: admin@123");

        System.out.println("[System] Customers → ");
        System.out.println("   " + c1.getUserId() + " | " + c1.getUserName() + " (123)");
        System.out.println("   " + c2.getUserId() + " | " + c2.getUserName() + " (123)");
        System.out.println("   " + c3.getUserId() + " | " + c3.getUserName() + " (123)");
        System.out.println("   " + c4.getUserId() + " | " + c4.getUserName() + " (123)");

        System.out.println("[System] Delivery Partners → ");
        System.out.println("   " + dp1.getUserId() + " | " + dp1.getUserName() + " (123)");
        System.out.println("   " + dp2.getUserId() + " | " + dp2.getUserName() + " (123)");
        System.out.println("   " + dp3.getUserId() + " | " + dp3.getUserName() + " (123)");

        System.out.println("[System] Restaurants → ");
        System.out.println("   " + r1.getRestaurantId() + " | " + r1.getRestaurantName() + " (Rajkot)");
        System.out.println("   " + r2.getRestaurantId() + " | " + r2.getRestaurantName() + " (Surat)");
        System.out.println("   " + r3.getRestaurantId() + " | " + r3.getRestaurantName() + " (Ahmedabad)");

        System.out.println("------------------------------------------");
    }
}
