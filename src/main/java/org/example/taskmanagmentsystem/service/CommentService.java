package org.example.taskmanagmentsystem.service;

import org.example.taskmanagmentsystem.entity.Comment;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(long id) {
        return commentRepository.findById(id).get();
    }

    public Comment addComment(Comment comment) {
        return  commentRepository.save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }



}
