package org.example.taskmanagmentsystem.controller;

import lombok.extern.log4j.Log4j2;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.service.TaskServiceImpl;
import org.example.taskmanagmentsystem.swagger.TaskControllerSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/taskApi")
public class TaskController implements TaskControllerSwagger {

    @Autowired
    private TaskServiceImpl service;

    @PostMapping("/task")
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        log.info("Выполняется запрос");
        Task createdTask = service.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/task/author/{task_id}")
    public ResponseEntity<Task> updateTaskByAuthor(@PathVariable Long task_id, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = service.updateTaskForAuthor(task_id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/task/assignee/{task_id}/{status}")
    public ResponseEntity<Task> changeStatus(@PathVariable Long task_id, @PathVariable String status) {
        Task task = service.changeTaskStatusByAssignee(task_id, status);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<Task>> getTasksForAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Page<Task> tasks = service.getAuthorTasks(authorId, page, size, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<Page<Task>> getTasksForAssignee(
            @PathVariable Long assigneeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Page<Task> tasks = service.getAssigneeTasks(assigneeId, page, size, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/task/{id}")
    public String deleteTask(@PathVariable Long id) {
        service.deleteTaskForAuthor(id);
        return "Task was deleted";
    }

}
