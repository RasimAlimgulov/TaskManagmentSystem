package org.example.taskmanagmentsystem;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Before;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.enums.Priority;
import org.example.taskmanagmentsystem.enums.Status;
import org.example.taskmanagmentsystem.security.RegistrationRequest;
import org.example.taskmanagmentsystem.service.TaskService;
import org.example.taskmanagmentsystem.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
class TaskManagmentSystemApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TaskService taskService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserDetails userDetails;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    private static String jwtToken;

    @BeforeAll
    public static void setUp() {
        jwtToken = "Bearer " + TestTokenUtil.generateTestToken("petya@mail.ru");
    }

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Task Title", "Task Description", Status.PENDING, Priority.HIGH, 2L);
        Task task = Task.builder().id(1L).title("Task Title").description("Task Description").status(Status.PENDING).priority(Priority.HIGH).assignee(new User()).author(new User()).build();

        UserDetails mockUserDetails = new org.springframework.security.core.userdetails.User("petya@mail.ru", "", new ArrayList<>());
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(task);
        when(userService.loadUserByUsername("petya@mail.ru")).thenReturn(mockUserDetails);

        log.info("Выполнется тестовый запрос");
        mockMvc.perform(post("/taskApi/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO))
                        .header("Authorization", jwtToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                        .value("Task Title"))
                .andExpect(jsonPath("$.description").value("Task Description"));
    }

    @Test
    public void registrationUser() throws Exception {
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password");
        registrationRequest.setRole("ADMIN");

        when(passwordEncoder.encode(registrationRequest.getPassword())).thenReturn("encodedPassword");
        doNothing().when(userService).createUser(any(User.class));
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationRequest)))
                .andExpect(status().isOk()).andExpect(content()
                        .string("User registered successfully"));

    }
}
