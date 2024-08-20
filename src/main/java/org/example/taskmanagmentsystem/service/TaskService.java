package org.example.taskmanagmentsystem.service;

import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.springframework.data.domain.Page;

public interface TaskService {
    Task createTask(TaskDTO taskDTO);
    Task changeTaskStatusByAssignee(Long id, String  status);
    Task updateTaskForAuthor(Long id, TaskDTO taskDTO);
    void deleteTaskForAuthor(Long id);
    Page<Task> getAuthorTasks(Long authorId, int page, int size, String sortBy);
    Page<Task> getAssigneeTasks(Long assigneeId, int page, int size, String sortBy);
}
