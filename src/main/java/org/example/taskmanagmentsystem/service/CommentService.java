package org.example.taskmanagmentsystem.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.taskmanagmentsystem.entity.Comment;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.User;
import org.example.taskmanagmentsystem.exeption_handing.YouCantDeleteOtherComments;
import org.example.taskmanagmentsystem.repository.CommentRepository;
import org.example.taskmanagmentsystem.repository.TaskRepository;
import org.example.taskmanagmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public Comment addComment(Long taskId, String content) {
        Task task;
        try{task = taskRepository.findById(taskId).get();}
        catch (NullPointerException e){throw new EntityNotFoundException("Task not found");}
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(currentName).get();
        Comment comment = Comment.builder()
                .task(task)
                .user(user)
                .content(content)
                .build();

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        String currentName = SecurityContextHolder.getContext().getAuthentication().getName();
        Comment comment=commentRepository.findById(id).get();
        if(!comment.getUser().getEmail().equals(currentName)){
            throw new YouCantDeleteOtherComments();
        }
        commentRepository.deleteById(id);
    }
}
