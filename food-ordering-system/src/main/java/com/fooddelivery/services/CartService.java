package com.fooddelivery.services;

import com.fooddelivery.exceptions.InvalidItemAddedToCart;
import com.fooddelivery.exceptions.MenuItemNotFoundException;
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

    public void addItemToCart(Customer customer, String restaurantId, String menuItemId, int quantity){
        if(quantity <= 0){
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

        if(cartMap.containsKey(menuItemId)) {
            CartItem cartItem = cartMap.get(menuItemId);
            int oldQuantity = cartItem.getQuantity();

            if ((oldQuantity + quantity > 10)) {
                cartItem.setQuantity(10);
                throw new InvalidItemAddedToCart("Quantity limit exceeded for item " + menuItemId + " (max: 10). Quantity set to 10.");
            }

            cartItem.setQuantity(oldQuantity + quantity);
        }else{
            CartItem cartItem = new CartItem(menuItem, quantity);
            cartMap.put(menuItemId, cartItem);
        }

        System.out.println("Menu item with ID: " + menuItemId + " is added to the cart.");

    }

    public void removeItemFromCart(Customer customer, String menuItemId, int quantity){
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity can't be negative");
        }

        Cart cart = customer.getCart();
        Map<String, CartItem> cartMap = cart.getCartItemMap();


        if(cartMap.containsKey(menuItemId)){
            CartItem cartItem = cartMap.get(menuItemId);
            int oldQuantity = cartItem.getQuantity();

            if(oldQuantity > quantity){
                cartItem.setQuantity(oldQuantity - quantity);
                System.out.println("Menu item with ID: " + menuItemId + "'s quantity has been change to the "  + cartItem.getQuantity());
            }else{
                cartMap.remove(menuItemId);
                System.out.println("Menu item with ID: " + menuItemId + " removed from the cart.");
            }

        }else{
            throw new MenuItemNotFoundException("Menu item with ID: " + menuItemId + " doesn't exist in the cart.");
        }
    }

    public void viewCart(Customer customer) {
        if (customer.getCart() == null || customer.getCart().getCartItemMap().isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        System.out.println("\n================= YOUR CART =================");
        System.out.println("Restaurant ID: " + customer.getCart().getCurrentRestaurantId());
        System.out.println("---------------------------------------------");
        System.out.printf("%-10s %-20s %-10s %-10s\n", "ID", "Item Name", "Price", "Qty");
        System.out.println("---------------------------------------------");
        double total = 0;
        for (CartItem ci : customer.getCart().getCartItemMap().values()) {
            System.out.printf("%-10s %-20s ₹%-9.2f %-10d\n",
                    ci.getMenuItem().getMenuItemId(),
                    ci.getMenuItem().getItemName(),
                    ci.getMenuItem().getPrice(),
                    ci.getQuantity());
            total += ci.getMenuItem().getPrice() * ci.getQuantity();
        }
        System.out.println("---------------------------------------------");
        System.out.printf("Total Cart Value: ₹%.2f\n", total);
        System.out.println("=============================================");
    }


}
