package org.example.taskmanagmentsystem.controller;

import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired
    private TaskService service;
    @GetMapping("/task")
    public List<Task> getAllTasks(){
        return service.getAllTasks();
    }

    @GetMapping("/task/{id}")
    public Task getTask(@PathVariable long id){
       return service.getTaskById(id);
    }
    @PostMapping("/task")
    public Task addTask(@RequestBody Task task){
        service.addTask(task);
        return task;
    }
    @PutMapping("/task")
    public Task updateDancer(@RequestBody Task task){
        service.addTask(task);
        return task;
    }
    @DeleteMapping("/task/{id}")
    public String  deleteTask(@PathVariable int id){
        service.deleteTask(id);
        return "Task was deleted successful";
    }

}
