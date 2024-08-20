package org.example.taskmanagmentsystem.controller;

import org.example.taskmanagmentsystem.entity.Comment;
import org.example.taskmanagmentsystem.service.CommentServiceImpl;
import org.example.taskmanagmentsystem.swagger.CommentControllerSwagger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commentApi")
public class CommentController implements CommentControllerSwagger {

    @Autowired
    private CommentServiceImpl service;

    @PostMapping("/")
    public ResponseEntity<Comment> addComment(
            @RequestParam Long taskId,
            @RequestParam String content) {
        Comment comment = service.addComment(taskId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}