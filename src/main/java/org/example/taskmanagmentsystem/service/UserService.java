package com.rasimalimgulov.userservice.user_service.service;

import com.rasimalimgulov.userservice.user_service.entity.User;

import java.util.List;

public interface UserService {
    User addUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
}
