package org.example.taskmanagmentsystem.service;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
@Log4j2
@Service
public class UserServiceImpl implements UserDetailsService,UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUserName(String email){
        return userRepository.findByEmail(email);
    }
    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Ищем юзера с именем "+email);

        User user =findByUserName(email).get();
        if (user == null) {
            log.info("Нет юзера с таким именем");
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
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
