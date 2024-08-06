package org.example.taskmanagmentsystem.controller;

import org.example.taskmanagmentsystem.entity.Comment;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentApi")
public class CommentController {
    @Autowired
    private CommentService service;

    @PostMapping("/")
    public ResponseEntity<Comment> addComment(
            @RequestParam Long taskId,
            @RequestParam Long userId,
            @RequestParam String content) {
        Comment comment = service.addComment(taskId, userId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}
