package com.fooddelivery.exceptions;

public class InvalidItemAddedToCart extends RuntimeException {
    public InvalidItemAddedToCart(String message) {
        super(message);
    }
}
