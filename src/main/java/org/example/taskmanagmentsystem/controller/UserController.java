package org.example.taskmanagmentsystem.controller;
import lombok.extern.log4j.Log4j2;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.security.AuthRequest;
import org.example.taskmanagmentsystem.security.AuthResponse;
import org.example.taskmanagmentsystem.security.JwtUtil;
import org.example.taskmanagmentsystem.security.RegistrationRequest;
import org.example.taskmanagmentsystem.service.UserServiceImpl;
import org.example.taskmanagmentsystem.swagger.UserControllerSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/auth")
public class UserController implements UserControllerSwagger {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        try {
            String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());
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
        log.info("Вызвался метод логирования");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            log.info("Успешно прошла процедура authentication");
        } catch (Exception e) {
            throw new Exception("Incorrect email or password", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        log.info("Получили UserDetails");
        final String jwt = jwtUtil.generateToken(userDetails);
        log.info("Сгенерировали токен");
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userDetailsService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        userDetails.setId(id);
        User updatedUser = userDetailsService.updateUser(userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userDetailsService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}