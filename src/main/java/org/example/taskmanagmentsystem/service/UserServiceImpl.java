package com.rasimalimgulov.userservice.user_service.service;

import com.rasimalimgulov.userservice.user_service.dao.UserDAO;
import com.rasimalimgulov.userservice.user_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public User addUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User getUserById(long id) {
        return userDAO.findById(id).get();
    }


}
