package com.fooddelivery.services;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.exceptions.InvalidUserCredentialsException;
import com.fooddelivery.exceptions.InvalidUserTypeException;
import com.fooddelivery.exceptions.UserNotFoundException;
import com.fooddelivery.factory.UserFactory;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserType userType, String name, String password, String phoneNumber, String city){
        if (userType == null) {
            throw new InvalidUserTypeException("User type cannot be null");
        }

        User user = UserFactory.createuser(userType, name, password, phoneNumber, city);

        userRepository.addUser(user);
        System.out.println(userType + " registered successfully! Assigned ID: " + user.getUserId());
        return user;
    }

    public User login(String userId, String password, UserType userType){
        User user = userRepository.findUserById(userId.toUpperCase());
        if(user == null){
            throw new UserNotFoundException("Login Failed: User with ID [" + userId + "] not found.");
        }

        if(!(user.getPassword().equals(password))){
            throw new InvalidUserCredentialsException("Login Failed: Password doesn't match.");
        }

        if(!(user.getUserType().equals(userType))){
            throw new InvalidUserCredentialsException("Login Failed: User Type doesn't match.");
        }

        return user;
    }


    public void displayAllUser(){
        Map<String, User> allUserMap = userRepository.getAllUserMap();

        if(allUserMap.isEmpty()){
            System.out.println("No user registered yet.. ");
            return;
        }

        System.out.println("\n======================= GLOBAL USER DIRECTORY =======================");
        System.out.printf("| %-12s | %-18s | %-18s | %-12s |\n", "Account ID", "User Name", "Account Type", "City");
        System.out.println("+--------------+--------------------+--------------------+--------------+");

        for (User u : allUserMap.values()) {
            String accountType = u.getUserType().toString();

            System.out.printf("| %-12s | %-18s | %-18s | %-12s |\n",
                    u.getUserId(), u.getUserName(), accountType, u.getCity());
        }

        System.out.println("=====================================================================");
    }
}
