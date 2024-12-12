package ru.em.entity.task.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.em.entity.task.Status;

import java.util.UUID;

@Data
@NoArgsConstructor
public class StatusDto {

    private UUID userId;
    private UUID taskId;
    private Status status;
}