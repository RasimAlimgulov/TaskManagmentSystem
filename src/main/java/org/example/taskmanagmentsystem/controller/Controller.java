package com.rasimalimgulov.userservice.user_service.controller;

import com.rasimalimgulov.userservice.user_service.entity.User;
import com.rasimalimgulov.userservice.user_service.exeption_handing.NoInfoAboutNewUserException;
import com.rasimalimgulov.userservice.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "controller_methods")
@RestController
@RequestMapping("/users")
public class Controller {
    @Autowired
    UserService userService;


    @SneakyThrows  ///пробрасыввает исключения дальше
    @Operation( /////
            summary = "Метод getAllUsers",
            description = "Метод использует Service, получает всех user-ов и возвращает их в виде списка"
    )
    @GetMapping("/user")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }



    @PostMapping("/user")
    public User addUser(@RequestBody User user){
        if (user.getUsername()==null || user.getEmail()==null) throw new NoInfoAboutNewUserException();
           else return userService.addUser(user);
    }
}
