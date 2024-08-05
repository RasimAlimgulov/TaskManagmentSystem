package org.example.taskmanagmentsystem.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.security.AuthRequest;
import org.example.taskmanagmentsystem.security.AuthResponse;
import org.example.taskmanagmentsystem.security.JwtUtil;
import org.example.taskmanagmentsystem.security.RegistrationRequest;
import org.example.taskmanagmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            // Хеширование пароля перед сохранением
            String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

            // Создание нового пользователя
            User user = new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(encodedPassword);
            user.setRole(registrationRequest.getRole());

            userDetailsService.createUser(user);

            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Incorrect email or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwt));
    }



}