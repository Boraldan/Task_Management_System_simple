package ru.em.entity.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.em.entity.task.Comment;
import ru.em.entity.task.Priority;
import ru.em.entity.task.Status;

import java.util.List;
import java.util.UUID;
@Schema(description = "Модель задачи")
@Data
public class TaskDto {

    @Schema(description = "Уникальный идентификатор задачи", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID taskId;

    @Schema(description = "Заголовок задачи", example = "Добавить новую задачу")
    private String title;

    @Schema(description = "Описание задачи", example = "Подробное описание новой задачи")
    private String description;

    @Schema(description = "Статус задачи", example = "IN_PROGRESS")
    private Status status;

    @Schema(description = "Приоритет задачи", example = "HIGH")
    private Priority priority;

    @Schema(description = "Идентификатор менеджера задачи (исполнителя)", example = "d41d8cd98f00b204e9800998ecf8427e")
    private UUID managerId;

    @Schema(description = "Список идентификаторов пользователей, назначенных на задачу", example = "[\"d41d8cd98f00b204e9800998ecf8427e\", \"a29f2b3f-79d8-4e58-bc69-2b8b09ad23dc\"]")
    private List<UUID> usersList;

    @Schema(description = "Список комментариев к задаче", example = "[{\"userId\": \"d41d8cd98f00b204e9800998ecf8427e\", \"content\": \"Комментарий к задаче\"}]")
    private List<Comment> comments;

    @Schema(description = "Статус активности задачи. Если false, задача считается завершённой", example = "true")
    private Boolean isActive;

}
