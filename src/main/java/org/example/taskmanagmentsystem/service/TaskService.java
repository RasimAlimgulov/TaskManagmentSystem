package org.example.taskmanagmentsystem.service;

import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(long id) {
        return taskRepository.findById(id).get();
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public void deleteTask(long id) {
        taskRepository.deleteById(id);
    }


}
