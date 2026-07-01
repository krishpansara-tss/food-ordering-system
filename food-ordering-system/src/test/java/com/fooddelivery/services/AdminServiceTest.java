package com.fooddelivery.services;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.factory.UserFactory;
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
    private User superAdmin;

    @BeforeEach
    void init() {
        userRepository       = new UserRepository();
        orderRepository      = new OrderRepository();

        adminService = new AdminService(orderRepository, userRepository);
        superAdmin = UserFactory.createAdmin(UserType.SUPER_ADMIN, "Super", "123", "999", "Ahmedabad");
    }

    @Test
    void addAdmin_AddedToRepository() {

        adminService.addAdmin(superAdmin, "ADMIN1", "1234", "0987654321", "Rajkot", UserType.ADMIN);
        assertEquals(1, userRepository.getAllUserMap().size());
    }

    @Test
    void addAdmin_VarifyingWithId() {

        User admin = adminService.addAdmin(superAdmin, "ADMIN1", "1234", "0987654321", "Rajkot", UserType.ADMIN);
        assertEquals(admin, userRepository.getAllUserMap().get(admin.getUserId()));
    }

    @Test
    void addAdmin_VarifyingTypeOfAdmin() {

        User admin = adminService.addAdmin(superAdmin, "ADMIN1", "1234", "0987654321", "Rajkot", UserType.ADMIN);
        assertEquals(UserType.ADMIN, admin.getUserType());
    }

    @Test
    void addAdmin_NormalAdmin_ThrowsException() {
        User normalAdmin = UserFactory.createAdmin(UserType.ADMIN, "Normal", "123", "999", "Rajkot");
        assertThrows(IllegalArgumentException.class, () -> {
            adminService.addAdmin(normalAdmin, "ADMIN1", "1234", "0987654321", "Rajkot", UserType.ADMIN);
        });
    }
}