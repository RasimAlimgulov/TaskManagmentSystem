package org.example.taskmanagmentsystem.controller;

import org.example.taskmanagmentsystem.entity.Comment;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/")
public class CommentController {
    @Autowired
    private CommentService service;

    @GetMapping("/comment")
    public List<Comment> getAllComments(){
        return service.getAllComments();
    }

    @GetMapping("/comment/{id}")
    public Comment getComment(@PathVariable long id){
        return service.getCommentById(id);
    }
    @PostMapping("/comment}")
    public Comment addTask(@RequestBody Comment comment){
        service.addComment(comment);
        return comment;
    }
    @PutMapping("/comment")
    public Comment updateComment(@RequestBody Comment comment){
        service.addComment(comment);
        return comment;
    }
    @DeleteMapping("/comment/{id}")
    public String  deleteComment(@PathVariable int id){
        service.deleteComment(id);
        return "Comment was deleted successful";
    }
}
