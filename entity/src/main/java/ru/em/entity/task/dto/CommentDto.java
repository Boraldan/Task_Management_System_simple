package ru.em.entity.task.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CommentDto {

    private UUID userId;
    private UUID taskId;
    private UUID commentId;
    private String content;
}