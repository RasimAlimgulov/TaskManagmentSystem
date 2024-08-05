package org.example.taskmanagmentsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.taskmanagmentsystem.entity.enums.Priority;
import org.example.taskmanagmentsystem.entity.enums.Status;

import java.util.List;

@Entity
@Table(name = "task_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // реализует паттерн buider
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false,name = "title")
    String title;

    @Column(nullable = false,name = "description")
    String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "status")
    Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "priority")
    Priority priority;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id",nullable = false)
    User assignee;

    @OneToMany(mappedBy = "task")
    List<Comment> comments;
}
