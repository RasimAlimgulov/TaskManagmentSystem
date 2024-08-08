package org.example.taskmanagmentsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;

import org.example.taskmanagmentsystem.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Log4j2
@Tag(name = "TaskController")
@RestController
@RequestMapping("/taskApi")
public class TaskController {

    @Autowired
    private TaskService service;

    @PostMapping("/task")
    @Operation(summary = "Создание задачи",
            description = "Создаёт задачу для текущего пользователя. Требуется предоставить JSON данные в теле запроса.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Задача успешно создана",
                            content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании задачи")
            })
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        log.info("Выполняется запрос");
        Task createdTask = service.createTask(taskDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/task/author/{task_id}")
    @Operation(summary = "Обновление задачи автором",
            description = "Изменяет задачу по её id (можно изменить только свои задачи). Требуется предоставить JSON данные в теле запроса.",
            parameters = @Parameter(name = "task_id", description = "ID задачи", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
                            content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена")
            })
    public ResponseEntity<Task> updateTaskByAuthor(@PathVariable Long task_id, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = service.updateTaskForAuthor(task_id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/task/assignee/{task_id}/{status}")
    @Operation(summary = "Изменение статуса задачи",
            description = "Изменяет статус закреплённой задачи (только свои задачи).",
            parameters = {
                    @Parameter(name = "task_id", description = "ID задачи", required = true),
                    @Parameter(name = "status", description = "Новый статус задачи", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статус задачи успешно обновлен",
                            content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена")
            })
    public ResponseEntity<Task> changeStatus(@PathVariable Long task_id, @PathVariable String status) {
        Task task = service.changeTaskStatusByAssignee(task_id, status);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/author/{authorId}")
    @Operation(summary = "Получение задач автора",
            description = "Возвращает отсортированную страницу задач определённого автора. Параметры пагинации и сортировки передаются через query-параметры.",
            parameters = @Parameter(name = "authorId", description = "ID автора", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи успешно найдены",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "404", description = "Автор не найден")
            })
    public ResponseEntity<Page<Task>> getTasksForAuthor(
            @PathVariable Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Page<Task> tasks = service.getAuthorTasks(authorId, page, size, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/assignee/{assigneeId}")
    @Operation(summary = "Получение задач исполнителя",
            description = "Возвращает отсортированную страницу задач определённого исполнителя. Параметры пагинации и сортировки передаются через query-параметры.",
            parameters = @Parameter(name = "assigneeId", description = "ID исполнителя", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи успешно найдены",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "404", description = "Исполнитель не найден")
            })
    public ResponseEntity<Page<Task>> getTasksForAssignee(
            @PathVariable Long assigneeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Page<Task> tasks = service.getAssigneeTasks(assigneeId, page, size, sortBy);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Удаление задачи",
            description = "Удаляет задачу по её ID. Может быть выполнено только автором задачи.",
            parameters = @Parameter(name = "id", description = "ID задачи", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена")
            })
    public String deleteTask(@PathVariable Long id) {
        service.deleteTaskForAuthor(id);
        return "Task was deleted";
    }

}
