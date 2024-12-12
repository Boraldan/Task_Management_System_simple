package ru.em.task.service.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.em.entity.task.dto.TaskDto;

public class TaskValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(TaskDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        TaskDto taskDto = (TaskDto) target;

        if (taskDto.getTitle() == null || taskDto.getTitle().trim().isEmpty()) {
            errors.rejectValue("title", "title.empty", "Title is required and cannot be empty.");
        } else if (taskDto.getTitle().length() < 3 || taskDto.getTitle().length() > 100) {
            errors.rejectValue("title", "title.size", "Title must be between 3 and 100 characters.");
        }

        if (taskDto.getDescription() != null && taskDto.getDescription().length() > 500) {
            errors.rejectValue("description", "description.size", "Description cannot exceed 500 characters.");
        }

        if (taskDto.getStatus() == null) {
            errors.rejectValue("status", "status.null", "Status is required.");
        }

        if (taskDto.getPriority() == null) {
            errors.rejectValue("priority", "priority.null", "Priority is required.");
        }

        if (taskDto.getManagerId() == null) {
            errors.rejectValue("managerId", "managerId.null", "Manager ID is required.");
        }


    }
}
