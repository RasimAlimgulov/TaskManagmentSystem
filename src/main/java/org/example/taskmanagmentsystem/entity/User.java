package org.example.taskmanagmentsystem.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Entity
@Table(name = "user_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // реализует паттерн buider
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true, name = "email")
    String email;

    @Column(nullable = false, name = "password")
    String password;

    @Column(nullable = false, name = "role")
    String role;

    @OneToMany(mappedBy = "author")
    List<Task> createdTask;

    @OneToMany(mappedBy = "assignee")
    List<Task> assignedTasks;

    @OneToMany(mappedBy = "user")
    List<Comment> comments;
}
