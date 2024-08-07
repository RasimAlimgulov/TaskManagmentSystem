package org.example.taskmanagmentsystem.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanagmentsystem.entity.Comment;
import org.example.taskmanagmentsystem.entity.Task;
import org.example.taskmanagmentsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "CommentController", description = "Контроллер для управления комментариями")
@RestController
@RequestMapping("/commentApi")
public class CommentController {

    @Autowired
    private CommentService service;

    @PostMapping("/")
    @Operation(summary = "Добавление комментария",
            description = "Добавляет комментарий к задаче. Требуется указать ID задачи и содержание комментария.",
            parameters = {
                    @Parameter(name = "taskId", description = "ID задачи, к которой добавляется комментарий", required = true),
                    @Parameter(name = "content", description = "Содержание комментария", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "201", description = "Комментарий успешно добавлен",
                            content = @Content(schema = @Schema(implementation = Comment.class))),
                    @ApiResponse(responseCode = "400", description = "Ошибка при добавлении комментария")
            })
    public ResponseEntity<Comment> addComment(
            @RequestParam Long taskId,
            @RequestParam String content) {
        Comment comment = service.addComment(taskId, content);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление комментария",
            description = "Удаляет комментарий по его ID. Может быть выполнено только автором комментария.",
            parameters = @Parameter(name = "id", description = "ID комментария", required = true),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Комментарий успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Комментарий не найден")
            })
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        service.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

}