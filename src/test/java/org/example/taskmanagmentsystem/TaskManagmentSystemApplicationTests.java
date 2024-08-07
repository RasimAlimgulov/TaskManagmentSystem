package org.example.taskmanagmentsystem;

import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.enums.Priority;
import org.example.taskmanagmentsystem.enums.Status;
import org.example.taskmanagmentsystem.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@SpringBootTest
@AutoConfigureMockMvc
class TaskManagmentSystemApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TaskService taskService;
    String emailUser="abc@google.com"; //////// Введите mail тестируемого User-a
    private String jwtToken="Bearer "+TestTokenUtil.generateTestToken(emailUser);

    @Test
    public void testCreateTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO("Task Title", "Task Description", Status.PENDING, Priority.HIGH, 1L);
        Task task = Task.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .status(Status.PENDING)
                .priority(Priority.HIGH)
                .assignee(new User())
                .author(new User())
                .build();

        when(taskService.createTask(any(TaskDTO.class))).thenReturn(task);

        mockMvc.perform(post("/taskApi/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)).header("Authorization",jwtToken))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Task Title"))
                .andExpect(jsonPath("$.description").value("Task Description"));
    }
}
