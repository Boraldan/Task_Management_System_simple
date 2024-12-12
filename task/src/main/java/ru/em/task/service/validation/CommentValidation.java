package ru.em.task.service.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.em.entity.task.dto.CommentDto;

public class CommentValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CommentDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CommentDto commentDto = (CommentDto) target;

        if (commentDto.getUserId() == null) {
            errors.rejectValue("userId", "NotNull", "User ID cannot be null.");
        }

        if (commentDto.getTaskId() == null) {
            errors.rejectValue("taskId", "NotNull", "Task ID cannot be null.");
        }

        if (commentDto.getContent() == null || commentDto.getContent().trim().isEmpty()) {
            errors.rejectValue("content", "NotEmpty", "Content cannot be empty.");
        } else if (commentDto.getContent().length() > 500) {
            errors.rejectValue("content", "Size", "Content cannot exceed 500 characters.");
        }
    }
}