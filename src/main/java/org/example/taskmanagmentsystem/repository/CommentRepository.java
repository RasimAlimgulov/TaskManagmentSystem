package org.example.taskmanagmentsystem.repository;

import org.example.taskmanagmentsystem.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
