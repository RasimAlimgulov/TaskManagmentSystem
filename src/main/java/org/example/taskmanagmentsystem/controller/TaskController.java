package org.example.taskmanagmentsystem.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.enums.Status;
import org.example.taskmanagmentsystem.exeption_handing.IncorrectStatus;
import org.example.taskmanagmentsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@Tag(name = "TaskController")
@RestController
@RequestMapping("/taskApi")
public class TaskController {

    @Autowired
    private TaskService service;
    @PostMapping("/task")        /////Создаёт задачу для Текущего пользователь
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task createdTask = service.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PutMapping("/task/author/{task_id}")             ////// Изменяем СВОЮ задачу по её id (чушие задачи невозможно изменить)
    public ResponseEntity<Task> updateTaskByAuthor(@PathVariable Long task_id, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = service.updateTaskForAuthor(task_id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }
    @PutMapping("/task/assignee/{task_id}/{status}")     ///Изменяем статус закреплённой задачи (Только свои)
    public ResponseEntity<Task> changeStatus(@PathVariable Long task_id, String  status){
        Task task=service.changeTaskStatusByAssignee(task_id,status);
        return ResponseEntity.ok(task);
    }
    @GetMapping("/author/{authorId}")        //////////////Получаем отсортированную страницу с 10 (default) задачами определённого автора
    public ResponseEntity<Page<Task>> getTasksForAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Page<Task> tasks = service.getAuthorTasks(authorId, page, size, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignee/{assigneeId}")            //////////////Получаем отсортированную страницу с 10 (default) задачами определённого закреплённого пользователя
    public ResponseEntity<Page<Task>> getTasksForAssignee(
            @PathVariable Long assigneeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Page<Task> tasks = service.getAssigneeTasks(assigneeId, page, size, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/task/{id}")                   ////////Удаляем (Может только автор задачи)
    public String deleteTask(@PathVariable Long id) {
        service.deleteTaskForAuthor(id);
        return "Task was deleted";

    }

}
