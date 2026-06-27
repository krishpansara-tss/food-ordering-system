package com.fooddelivery.repository;

import com.fooddelivery.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private Map<String, User> userMap = new HashMap<>();

    public void addUser(User user){
        userMap.put(user.getUserId(), user);
    }

    public User findUserById(String userId){
        return userMap.get(userId);
    }

    public Map<String, User> getAllUserMap() {
        return userMap;
    }
}
