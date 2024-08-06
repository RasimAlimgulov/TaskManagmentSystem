package org.example.taskmanagmentsystem.service;

import jakarta.transaction.Transactional;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUserName(String email){
        return userRepository.findByEmail(email);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =findByUserName(email).orElseThrow(()->new UsernameNotFoundException("Пользователь с эти именем "+email+" не найден"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword()
                , Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

    public void createUser(User user){
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
