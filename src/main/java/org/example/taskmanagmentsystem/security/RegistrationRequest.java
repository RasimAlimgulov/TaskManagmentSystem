package org.example.taskmanagmentsystem.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationRequest {
    private String email;
    private String password;
    private String role;
}