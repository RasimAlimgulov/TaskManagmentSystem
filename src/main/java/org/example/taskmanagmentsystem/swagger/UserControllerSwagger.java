package org.example.taskmanagmentsystem.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.security.AuthRequest;
import org.example.taskmanagmentsystem.security.AuthResponse;
import org.example.taskmanagmentsystem.security.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
@Tag(name = "UserController", description = "Контроллер для управления пользователями и аутентификации")
public interface UserControllerSwagger {

    @Operation(summary = "Регистрация пользователя",
            description = "Регистрация нового пользователя для дальнейшего получения JWT токена. Необходимо предоставить JSON данные: email, password и role (USER или ADMIN)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = RegistrationRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегистрирован"),
                    @ApiResponse(responseCode = "400", description = "Ошибка регистрации")
            })
    ResponseEntity<?> registerUser(RegistrationRequest registrationRequest);

    @Operation(summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя с использованием email и password. Возвращает JWT токен при успешной аутентификации",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = AuthRequest.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Аутентификация успешна",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Неправильный email или пароль")
            })
    ResponseEntity<?> createAuthenticationToken(AuthRequest authRequest) throws Exception;


    @Operation(summary = "Получение пользователя по email",
            description = "Возвращает информацию о пользователе по заданному email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь найден",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    ResponseEntity<User> getUserByEmail(String email);

    @Operation(summary = "Обновление данных пользователя",
            description = "Обновляет информацию о пользователе по заданному ID",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = User.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь обновлен",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    ResponseEntity<User> updateUser(Long id, User userDetails);

    @Operation(summary = "Удаление пользователя",
            description = "Удаляет пользователя по заданному ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Пользователь удален"),
                    @ApiResponse(responseCode = "404", description = "Пользователь не найден")
            })
    ResponseEntity<Void> deleteUser( Long id);
}
