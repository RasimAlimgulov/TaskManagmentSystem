package org.example.taskmanagmentsystem.controller;

import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/taskApi")
public class TaskController {

    @Autowired
    private TaskService service;
    @PostMapping("/task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = service.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task updatedTask = service.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }

    @PostMapping("/task/{taskId}/assign/{userId}")
    public ResponseEntity<Task> assignTask(@PathVariable Long taskId, @PathVariable Long userId) {
        Task assignedTask = service.assignTask(taskId, userId);
        return ResponseEntity.ok(assignedTask);
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        service.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/task/{userId}")
    public ResponseEntity<List<Task>> getTasksForUser(@PathVariable Long userId) {
        List<Task> tasks = service.getTasksForUser(userId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task")
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = service.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

}
