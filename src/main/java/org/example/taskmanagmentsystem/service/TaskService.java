package org.example.taskmanagmentsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.entity.enums.Priority;
import org.example.taskmanagmentsystem.repository.TaskRepository;
import org.example.taskmanagmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(TaskDTO taskDTO) {
        User assignee=userRepository.findById(taskDTO.getAssigneeId()).orElseThrow(()->new UsernameNotFoundException("Вы ввели id не существующего юзера"));
        User author=userRepository.findById(taskDTO.getAuthorId()).orElseThrow(()->new UsernameNotFoundException("Вы ввели id не существующего юзера"));
        Task newtask = Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .status(taskDTO.getStatus())
                .priority(taskDTO.getPriority())
                .assignee(assignee)
                .author(author)
                .build();
        return taskRepository.save(newtask);
    }

    public Task updateTask(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        task.setPriority(taskDetails.getPriority());
        task.setAssignee(taskDetails.getAssignee());
        return taskRepository.save(task);
    }

    public Task assignTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new EntityNotFoundException("Task not found"));
        User assignee = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return taskRepository.findByAssignee(user);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
