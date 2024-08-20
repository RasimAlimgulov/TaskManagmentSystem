package org.example.taskmanagmentsystem.service;

import org.example.taskmanagmentsystem.entity.Comment;

public interface CommentService {
    Comment addComment(Long taskId, String content);
    void deleteComment(Long id);
}
