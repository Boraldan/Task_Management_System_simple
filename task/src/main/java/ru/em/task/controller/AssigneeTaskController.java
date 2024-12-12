package ru.em.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.em.entity.task.Comment;
import ru.em.entity.task.MyTask;
import ru.em.entity.task.dto.CommentDto;
import ru.em.entity.task.dto.TaskDto;
import ru.em.task.service.api.TaskService;
import ru.em.task.service.validation.CommentValidation;

import java.util.UUID;

@RestController
@RequestMapping("/api/assignee")
@RequiredArgsConstructor
public class AssigneeTaskController {

    private final TaskService taskService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new CommentValidation());
    }

    /**
     * Метод для получения задач, назначенных пользователю по его идентификатору.
     * Этот метод позволяет получить список задач с возможностью пагинации.
     *
     * @param userId Идентификатор пользователя, для которого нужно получить задачи.
     * @param page   Номер страницы (по умолчанию 0).
     * @param size   Количество элементов на странице (по умолчанию 10).
     * @return Возвращает список задач, назначенных пользователю.
     */
    @Operation(summary = "Получить задачи по пользователю", description = "Возвращает задачи, назначенные пользователю, с поддержкой пагинации.")
    @GetMapping("/tasks")
    public ResponseEntity<Page<MyTask>> getTasksByUserId(@RequestParam UUID userId,
                                                         @RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyTask> tasks = taskService.getTasksByUserId(userId, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Метод для обновления статуса задачи.
     * Этот метод позволяет изменить статус задачи на основе данных, переданных в TaskDto.
     *
     * @param taskDto Данные для обновления статуса задачи.
     * @return Возвращает обновленную задачу.
     */
    @Operation(summary = "Обновить статус задачи", description = "Обновляет статус задачи на основе переданных данных в TaskDto.")
    @PatchMapping("/task/status/update")
    public ResponseEntity<MyTask> updateTaskStatus(@RequestBody TaskDto taskDto) {
        MyTask myTask = taskService.updateTask(taskDto);
        return ResponseEntity.ok(myTask);
    }

    /**
     * Метод для добавления комментария к задаче.
     * Этот метод позволяет добавить комментарий к задаче на основе данных, переданных в CommentDto.
     *
     * @param commentDto Данные для добавления комментария к задаче.
     * @return Возвращает созданный комментарий.
     */
    @Operation(summary = "Добавить комментарий к задаче", description = "Добавляет комментарий к задаче на основе переданных данных в CommentDto.")
    @PostMapping("/task/comment/creat")
    public ResponseEntity<Comment> addComment(@RequestBody @Validated CommentDto commentDto) {
        Comment comment = taskService.addComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

}
