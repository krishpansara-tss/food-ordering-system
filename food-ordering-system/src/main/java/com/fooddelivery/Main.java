package com.fooddelivery;

import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.services.*;
import com.fooddelivery.util.LoadDummyData;
import com.fooddelivery.menu.AuthenticationMenu;

public class Main {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        RestaurantRepository restaurantRepository = new RestaurantRepository();
        OrderRepository orderRepository = new OrderRepository();

        LoadDummyData.seed(userRepository, restaurantRepository);

        UserService userService = new UserService(userRepository);
        RestaurantService restaurantService = new RestaurantService(restaurantRepository);
        CartService cartService = new CartService(userRepository, restaurantRepository);
        OrderService orderService = new OrderService(orderRepository, userRepository, restaurantRepository);
        AdminService adminService = new AdminService(orderRepository, userRepository);
        DeliveryPartnerService deliveryPartnerService = new DeliveryPartnerService();

        AuthenticationMenu authMenu = new AuthenticationMenu(userService, restaurantService, cartService, orderService, adminService, deliveryPartnerService);
        authMenu.authenticationMenu();
    }
}
