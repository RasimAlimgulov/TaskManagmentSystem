package org.example.taskmanagmentsystem.service;

import org.example.taskmanagmentsystem.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String email);
    void createUser(User user);
    User findByEmail(String email);
    User updateUser(User user);
    void deleteUser(Long id);
}
