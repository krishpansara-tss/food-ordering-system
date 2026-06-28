package com.fooddelivery.services;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.model.*;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    private AdminService adminService;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    @BeforeEach
    void init() {
        userRepository       = new UserRepository();
        orderRepository      = new OrderRepository();

        adminService = new AdminService(orderRepository, userRepository);
    }

    @Test
    void addAdmin_AddedToRepository() {

        adminService.addAdmin("ADMIN1", "1234", "0987654321", "Rajkot");
        assertEquals(1, userRepository.getAllUserMap().size());
    }

    @Test
    void addAdmin_VarifyingWithId() {

        User admin = adminService.addAdmin("ADMIN1", "1234", "0987654321", "Rajkot");
        assertEquals(admin, userRepository.getAllUserMap().get(admin.getUserId()));
    }

    @Test
    void addAdmin_VarifyingTypeOfAdmin() {

        User admin = adminService.addAdmin("ADMIN1", "1234", "0987654321", "Rajkot");
        assertEquals(UserType.ADMIN, admin.getUserType());
    }
}