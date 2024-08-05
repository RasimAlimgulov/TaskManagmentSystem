package org.example.taskmanagmentsystem.repository;

import org.example.taskmanagmentsystem.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
