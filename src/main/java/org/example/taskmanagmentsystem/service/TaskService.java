package org.example.taskmanagmentsystem.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.entity.enums.Status;
import org.example.taskmanagmentsystem.exeption_handing.IncorrectStatus;
import org.example.taskmanagmentsystem.exeption_handing.NoAuthorWithThisId;
import org.example.taskmanagmentsystem.exeption_handing.YouCantChangeOtherTask;
import org.example.taskmanagmentsystem.exeption_handing.YouCantChangeStatusOfNotYourTask;
import org.example.taskmanagmentsystem.repository.TaskRepository;
import org.example.taskmanagmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
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
        User assignee = userRepository.findById(taskDTO.getAssigneeId())
                .orElseThrow(() -> new UsernameNotFoundException("Вы ввели id не существующего юзера"));
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        User author = userRepository.findByEmail(currentName).orElseThrow(() -> new UsernameNotFoundException("Текущий пользователь не найден"));
        log.info("Сейчас текущее email пользователя" + author.getEmail());
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

    public Task changeTaskStatusByAssignee(Long id, String  status) {

        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task=taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));

        if (!task.getAssignee().getEmail().equals(currentName)){
            throw new YouCantChangeStatusOfNotYourTask();
        }
        Status statusEnum;
        try{
           statusEnum =Status.valueOf(status);
        }catch (Exception e){
            throw new IncorrectStatus();
        }

       task.setStatus(statusEnum);
      return taskRepository.save(task);
    }

    public Task updateTaskForAuthor(Long id, TaskDTO taskDTO) {
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));;
        if (!task.getAuthor().getEmail().equals(currentName)) {
            throw new YouCantChangeOtherTask();
        }

        User assignee = userRepository.findById(taskDTO.getAssigneeId())
                .orElseThrow(() -> new UsernameNotFoundException("Вы ввели id не существующего юзера"));
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(taskDTO.getPriority());
        task.setAssignee(assignee);
        return taskRepository.save(task);
    }

    public void deleteTaskForAuthor(Long id) {
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Задача не найдена"));
        if (!task.getAuthor().getEmail().equals(currentName)) {
            throw new YouCantChangeOtherTask();
        }
        taskRepository.deleteById(id);
    }

    public Page<Task> getAuthorTasks(Long authorId, int page, int size, String sortBy) {
        User author = userRepository.findById(authorId)
                .orElseThrow(() -> new NoAuthorWithThisId());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return taskRepository.findAllByAuthor(author, pageable);
    }

    public Page<Task> getAssigneeTasks(Long assigneeId, int page, int size, String sortBy) {
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new NoAuthorWithThisId());

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return taskRepository.findAllByAssignee(assignee, pageable);
    }

}
