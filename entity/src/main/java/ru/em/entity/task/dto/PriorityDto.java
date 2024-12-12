package ru.em.entity.task.dto;

import lombok.Data;
import ru.em.entity.task.Priority;

import java.util.UUID;

@Data
public class PriorityDto {

    private UUID taskId;
    private Priority priority;

}
