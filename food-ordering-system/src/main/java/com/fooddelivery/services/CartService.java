package com.fooddelivery.services;

import com.fooddelivery.exceptions.InvalidItemAddedToCart;
import com.fooddelivery.exceptions.MenuItemNotFoundException;
import com.fooddelivery.exceptions.UserNotFoundException;
import com.fooddelivery.model.*;
import com.fooddelivery.repository.RestaurantRepository;
import com.fooddelivery.repository.UserRepository;

import java.util.Map;

public class CartService {

    private UserRepository userRepository;
    private RestaurantRepository restaurantRepository;


    public CartService(UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public CartService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addItemToCart(Customer customer, String restaurantId, String menuItemId, int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("Quantity can't be negative");
        }

        MenuItem menuItem = restaurantRepository.findMenuById(menuItemId);
        if(menuItem == null){
            throw new MenuItemNotFoundException("Menu item with ID: " + menuItemId + " doesn't exist");
        }

        Cart cart = customer.getCart();
        Map<String, CartItem> cartMap = cart.getCartItemMap();
        if(cart.getCurrentRestaurantId() == null){
            cart.setCurrentRestaurantId(restaurantId);
        }else{
            if(!(cart.getCurrentRestaurantId().equals(restaurantId))){
                throw new InvalidItemAddedToCart("You can only add the items from the same restaurant.");
            }
        }

        if(cartMap.containsKey(menuItemId)){
            CartItem cartItem = cartMap.get(menuItemId);
            int oldQuantity = cartItem.getQuantity();

            cartItem.setQuantity(oldQuantity + quantity);

            System.out.println("added to card");
        }else{
            CartItem cartItem = new CartItem(menuItem, quantity);
            cartMap.put(menuItemId, cartItem);
            System.out.println("added to card");
        }
    }
}
