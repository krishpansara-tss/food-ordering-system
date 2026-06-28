package com.fooddelivery.services;

import com.fooddelivery.exceptions.RestaurantNotFoundException;
import com.fooddelivery.model.MenuItem;
import com.fooddelivery.model.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantServiceTest {
    RestaurantRepository restaurantRepository;
    RestaurantService restaurantService;

    @BeforeEach
    void init(){
        restaurantRepository = new RestaurantRepository();
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    void createRestaurant_AddedToRepository() {
        restaurantService.createRestaurant("Ganesh", "0987654321", "Rajkot");

        assertEquals(1, restaurantRepository.getRestaurantList().size());
    }

    @Test
    void createRestaurant_ObjectVerifying() {
        Restaurant restaurant = restaurantService.createRestaurant("Ganesh", "0987654321", "Rajkot");

        assertEquals(restaurant, restaurantRepository.getRestaurantList().get(restaurant.getRestaurantId()));
    }


    @Test
    void loginIntoRestaurant_Existing() {
        Restaurant createdrestaurant = restaurantService.createRestaurant("Ganesh", "0987654321", "Rajkot");
        Restaurant loggedInRestaurant = restaurantService.loginIntoRestaurant(createdrestaurant.getRestaurantId());
        assertEquals(createdrestaurant, loggedInRestaurant);
    }

    @Test
    void loginIntoRestaurant_NotExist() {
        assertThrows(RestaurantNotFoundException.class, () ->
                restaurantService.loginIntoRestaurant("DMY_ID")
        );
    }

    @Test
    void addItemToRestaurant_ExistingRestaurant() {
        Restaurant restaurant = restaurantService.createRestaurant("Ganesh", "0987654321", "Rajkot");

        restaurantService.addItemToRestaurant(restaurant.getRestaurantId(), "Gujarati Dish", 120, true);

        assertEquals(1, restaurant.getMenuItemList().size());
    }

    @Test
    void addItemToRestaurant_NonExistingRestaurant() {
        assertThrows(RestaurantNotFoundException.class, () ->
                restaurantService.addItemToRestaurant("DMY_ID", "Gujarati Dish", 120, true)
        );
    }
}