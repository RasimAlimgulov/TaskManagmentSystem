package org.example.taskmanagmentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.taskmanagmentsystem.enums.Priority;
import org.example.taskmanagmentsystem.enums.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Long assigneeId;
}
