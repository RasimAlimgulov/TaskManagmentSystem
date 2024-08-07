package org.example.taskmanagmentsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
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
import org.springframework.web.bind.annotation.*;
@Tag(name = "UserController", description = "Контроллер для управления пользователями и аутентификации")
@Log4j2
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
    @Operation(summary = "Регистрация пользователя",
            description = "Регистрация нового пользователя для дальнейшего получения JWT токена. Необходимо предоставить JSON данные: email, password и role (USER или ADMIN)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = RegistrationRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
                    @ApiResponse(responseCode = "400", description = "Ошибка регистрации")
            })
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
    @Operation(summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя с использованием email и password. Возвращает JWT токен при успешной аутентификации",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = AuthRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Аутентификация успешна",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Неправильный email или пароль")
            })
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
    @Operation(summary = "Получение пользователя по email",
            description = "Возвращает информацию о пользователе по заданному email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User user = userDetailsService.findByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных пользователя",
            description = "Обновляет информацию о пользователе по заданному ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь обновлен",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        userDetails.setId(id);
        User updatedUser = userDetailsService.updateUser(userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление пользователя",
            description = "Удаляет пользователя по заданному ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь удален"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userDetailsService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}