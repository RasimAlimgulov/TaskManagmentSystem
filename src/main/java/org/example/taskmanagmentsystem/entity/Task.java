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
    private Long id;

    @Column(nullable = false,name = "title")
    private String title;

    @Column(nullable = false,name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,name = "priority")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    @Column(name = "author")
    private User author;

    @ManyToOne
    @JoinColumn(name = "assignee_id",nullable = false)
    @Column(name = "assignee")
    private User assignee;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
}
