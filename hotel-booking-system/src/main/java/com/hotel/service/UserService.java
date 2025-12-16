package com.hotel.service;

import com.hotel.dao.UserDAO;
import com.hotel.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean register(String username, String password, String fullName, String email) {
        // Basic validation
        if (username == null || password == null || fullName == null || email == null)
            return false;

        // Password length check
        if (password.length() < 6) {
            return false;
        }

        // Email uniqueness check
        if (userDAO.isEmailTaken(email)) {
            return false;
        }

        User user = new User(username, password, fullName, "CUSTOMER", email);
        return userDAO.registerUser(user);
    }

    public User login(String username, String password) {
        return userDAO.loginUser(username, password);
    }
}
