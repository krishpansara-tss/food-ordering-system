package com.fooddelivery.services;

import com.fooddelivery.enums.UserType;
import com.fooddelivery.exceptions.InvalidUserCredentialsException;
import com.fooddelivery.exceptions.InvalidUserTypeException;
import com.fooddelivery.exceptions.UserNotFoundException;
import com.fooddelivery.model.User;
import com.fooddelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    UserService userService;

    UserRepository userRepository;

    @BeforeEach
    void init(){
        userRepository = new UserRepository();
        userService  = new UserService(userRepository);
    }

//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

//    public User registerUser(UserType userType, String name, String password, String phoneNumber, String city){

    @Test
    void registerUser_AddedToRepository() {

        userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");
        assertEquals(1, userRepository.getAllUserMap().size());
    }


    @Test
    void registerUser_CheckingType() {

        User user = userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");
        assertEquals(UserType.CUSTOMER, user.getUserType());
    }


    @Test
    void registerUser_CheckingById() {

        User user = userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");
        assertEquals(user, userRepository.getAllUserMap().get(user.getUserId()));
    }

    @Test
    void registerUser_HavingNullUserType() {

        assertThrows(InvalidUserTypeException.class, () ->
                userService.registerUser(null, "Rushi", "123", "0987654321", "Rajkot")
        );
    }

    @Test
    void login_UserExist() {
        User createdUser = userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");
        User loggedInUser = userService.login(createdUser.getUserId(), createdUser.getPassword(), createdUser.getUserType());

        assertEquals(createdUser, loggedInUser);
    }

    @Test
    void login_UserNotExist() {
        User createdUser = userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");

        assertThrows(UserNotFoundException.class, () ->
                userService.login("DMY_ID", createdUser.getPassword(), createdUser.getUserType())
        );
    }

    @Test
    void login_InvalidUserCredentials() {
        User createdUser = userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");

        assertThrows(InvalidUserCredentialsException.class, () ->
                userService.login(createdUser.getUserId(), "DMY_PASS", createdUser.getUserType())
        );
    }

    @Test
    void login_TypeMismatch() {
        User createdUser = userService.registerUser(UserType.CUSTOMER, "Rushi", "123", "0987654321", "Rajkot");

        assertThrows(InvalidUserCredentialsException.class, () ->
                userService.login(createdUser.getUserId(), createdUser.getPassword(), UserType.ADMIN)
        );
    }
}