package org.example.taskmanagmentsystem.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.taskmanagmentsystem.entity.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
@Tag(name = "CommentController", description = "Контроллер для управления комментариями")
public interface CommentControllerSwagger {

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
    ResponseEntity<Comment> addComment( Long taskId, String content);

    @Operation(summary = "Удаление комментария",
            description = "Удаляет комментарий по его ID. Может быть выполнено только автором комментария.",
            parameters = @Parameter(name = "id", description = "ID комментария", required = true),
            responses = {
                    @ApiResponse(responseCode = "204", description = "Комментарий успешно удален"),
                    @ApiResponse(responseCode = "404", description = "Комментарий не найден")
            })
    ResponseEntity<Void> deleteComment(Long id);


}
