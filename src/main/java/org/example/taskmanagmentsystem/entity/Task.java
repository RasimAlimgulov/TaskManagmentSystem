package org.example.taskmanagmentsystem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.taskmanagmentsystem.enums.Priority;
import org.example.taskmanagmentsystem.enums.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
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
@EntityListeners(AuditingEntityListener.class)
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY )
    @OneToMany(mappedBy = "task")
    List<Comment> comments;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Version
    private int version;
}
