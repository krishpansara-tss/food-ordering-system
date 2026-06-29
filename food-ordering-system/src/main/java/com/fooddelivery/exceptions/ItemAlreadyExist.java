package com.fooddelivery.exceptions;

public class ItemAlreadyExist extends RuntimeException {
    public ItemAlreadyExist(String message) {
        super(message);
    }
}
