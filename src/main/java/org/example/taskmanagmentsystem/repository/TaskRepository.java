package org.example.taskmanagmentsystem.repository;

import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
   Page<Task> findAllByAuthor(User author, Pageable pageable);
   Page<Task> findAllByAssignee(User assignee, Pageable pageable);
}
