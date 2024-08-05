package org.example.taskmanagmentsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Table(name = "comment_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // реализует паттерн buider
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    Task task;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    User user;

    @Column(nullable = false)
    String content;
}
