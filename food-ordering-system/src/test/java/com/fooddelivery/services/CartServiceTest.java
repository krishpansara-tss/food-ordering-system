package com.fooddelivery.services;

import com.fooddelivery.enums.CuisineType;
import com.fooddelivery.exceptions.InvalidItemAddedToCart;
import com.fooddelivery.exceptions.MenuItemNotFoundException;
import com.fooddelivery.model.*;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private CartService cartService;
    private RestaurantRepository restaurantRepository;
    private Customer customer;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private MenuItem pizza;
    private MenuItem burger;

    @BeforeEach
    void init() {
        restaurantRepository = new RestaurantRepository();
        UserRepository userRepository = new UserRepository();
        cartService = new CartService(userRepository, restaurantRepository);

        customer = new Customer("Ravi Patel", "ravi@123", "9876543210", "Rajkot");

        restaurant1 = new Restaurant("Mavdi Chowk", "123", "9000000001", "Rajkot", "Nana Mauva");
        pizza = new MenuItem("Margherita Pizza", 199.0, true, CuisineType.GUJARATI);
        burger = new MenuItem("Paneer Burger", 149.0, true, CuisineType.GUJARATI);
        restaurant1.addMenuItem(pizza);
        restaurant1.addMenuItem(burger);

        restaurant2 = new Restaurant("Surat Locha Corner", "123","9000000002", "Surat", "Vastrapur");
        MenuItem locha = new MenuItem("Surat Locha", 50.0, true, CuisineType.GUJARATI);
        restaurant2.addMenuItem(locha);

        restaurantRepository.addRestaurant(restaurant1);
        restaurantRepository.addRestaurant(restaurant2);
    }


    @Test
    void addItemToCart_OneItem() {
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 2);

        Cart cart = customer.getCart();
        assertTrue(cart.getCartItemMap().containsKey(pizza.getMenuItemId()));
        assertEquals(2, cart.getCartItemMap().get(pizza.getMenuItemId()).getQuantity());
    }

    @Test
    void addItemToCart_SameItemMultipleTime() {
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 2);
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 3);

        int quantity = customer.getCart().getCartItemMap().get(pizza.getMenuItemId()).getQuantity();
        assertEquals(5, quantity);
    }

    @Test
    void addItemToCart_SetRestaurantIdOnFirstOrder() {
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 1);

        assertEquals(restaurant1.getRestaurantId(), customer.getCart().getCurrentRestaurantId());
    }

    @Test
    void addItemToCart_MultipleItemsSameRestaurant_ShouldAllBeInCart() {
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 1);
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), burger.getMenuItemId(), 2);

        Cart cart = customer.getCart();
        assertEquals(2, cart.getCartItemMap().size());
        assertTrue(cart.getCartItemMap().containsKey(pizza.getMenuItemId()));
        assertTrue(cart.getCartItemMap().containsKey(burger.getMenuItemId()));
    }


    @Test
    void addItemToCart_NegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () ->
                cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), -1)
        );
    }

    @Test
    void addItemToCart_InvalidMenuItemId() {
        assertThrows(MenuItemNotFoundException.class, () ->
                cartService.addItemToCart(customer, restaurant1.getRestaurantId(), "HAHA", 1)
        );
    }

    @Test
    void addItemToCart_DifferentRestaurant() {
        // rest 1
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 1);

        // rest 2
        MenuItem lochItem = restaurant2.getMenuItemList().values().iterator().next();
        assertThrows(InvalidItemAddedToCart.class, () ->
                cartService.addItemToCart(customer, restaurant2.getRestaurantId(), lochItem.getMenuItemId(), 1)
        );
    }

    @Test
    void removeItemFromCart_ExistingItem_ExistingQuantityRemoved(){
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 1);

        cartService.removeItemFromCart(customer, pizza.getMenuItemId(), 1);

        assertEquals(0, customer.getCart().getCartItemMap().size());
    }

    @Test
    void removeItemFromCart_ExistingItem_MoreThanExistingQuantityRemoved(){
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 3);

        cartService.removeItemFromCart(customer, pizza.getMenuItemId(), 55);

        assertEquals(0, customer.getCart().getCartItemMap().size());
    }

    @Test
    void removeItemFromCart_ExistingItem_LessThanExistingQuantityRemoved(){
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 3);

        cartService.removeItemFromCart(customer, pizza.getMenuItemId(), 1);

        assertEquals(2, customer.getCart().getCartItemMap().get(pizza.getMenuItemId()).getQuantity());
    }

    @Test
    void removeItemFromCart_NegativeQuantity(){
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 3);

        assertThrows(IllegalArgumentException.class, () ->
                cartService.removeItemFromCart(customer, pizza.getMenuItemId(), -2)
        );
    }

    @Test
    void removeItemFromCart_NonExistingItem(){
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 3);

        assertThrows(MenuItemNotFoundException.class, () ->
                cartService.removeItemFromCart(customer, "DMY_ID", 2)
        );
    }


    @Test
    void addItemToCart_AndThenChangePrice() {
        cartService.addItemToCart(customer, restaurant1.getRestaurantId(), pizza.getMenuItemId(), 2);

        Cart cart = customer.getCart();
        assertTrue(cart.getCartItemMap().containsKey(pizza.getMenuItemId()));
        assertEquals(pizza.getPrice(), cart.getCartItemMap().get(pizza.getMenuItemId()).getMenuItem().getPrice());
    }



}