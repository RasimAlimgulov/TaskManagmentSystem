package org.example.taskmanagmentsystem.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanagmentsystem.dto.TaskDTO;
import org.example.taskmanagmentsystem.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "TaskController")
public interface TaskControllerSwagger {

    @Operation(summary = "Создание задачи",
            description = "Создаёт задачу для текущего пользователя. Требуется предоставить JSON данные в теле запроса.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Задача успешно создана",
                            content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка при создании задачи")
            })
    ResponseEntity<Task> createTask( TaskDTO taskDTO);


    @Operation(summary = "Обновление задачи автором",
            description = "Изменяет задачу по её id (можно изменить только свои задачи). Требуется предоставить JSON данные в теле запроса.",
            parameters = @Parameter(name = "task_id", description = "ID задачи", required = true),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = TaskDTO.class))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача успешно обновлена",
                            content = @Content(schema = @Schema(implementation = Task.class))),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена")
            })
    ResponseEntity<Task> updateTaskByAuthor(Long task_id, TaskDTO taskDTO);

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
    ResponseEntity<Task> changeStatus(Long task_id, String status);


    @Operation(summary = "Получение задач автора",
            description = "Возвращает отсортированную страницу задач определённого автора. Параметры пагинации и сортировки передаются через query-параметры.",
            parameters = @Parameter(name = "authorId", description = "ID автора", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи успешно найдены",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "404", description = "Автор не найден")
            })
    ResponseEntity<Page<Task>> getTasksForAuthor(Long authorId, int page, int size, String sortBy);



    @Operation(summary = "Получение задач исполнителя",
            description = "Возвращает отсортированную страницу задач определённого исполнителя. Параметры пагинации и сортировки передаются через query-параметры.",
            parameters = @Parameter(name = "assigneeId", description = "ID исполнителя", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задачи успешно найдены",
                            content = @Content(schema = @Schema(implementation = Page.class))),
                    @ApiResponse(responseCode = "404", description = "Исполнитель не найден")
            })
    ResponseEntity<Page<Task>> getTasksForAssignee( Long assigneeId, int page, int size, String sortBy);


    @Operation(summary = "Удаление задачи",
            description = "Удаляет задачу по её ID. Может быть выполнено только автором задачи.",
            parameters = @Parameter(name = "id", description = "ID задачи", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Задача успешно удалена"),
                    @ApiResponse(responseCode = "404", description = "Задача не найдена")
            })
    String deleteTask( Long id);
}
