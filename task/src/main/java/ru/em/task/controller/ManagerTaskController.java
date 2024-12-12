package ru.em.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import ru.em.task.service.validation.TaskValidation;

import java.util.UUID;


@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerTaskController {

    private final TaskService taskService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(new TaskValidation());
        binder.addValidators(new CommentValidation());
    }

    /**
     * Получить список задач с пагинацией.
     * Этот метод возвращает список задач с возможностью пагинации.
     *
     * @param page Страница для пагинации (по умолчанию 0).
     * @param size Размер страницы для пагинации (по умолчанию 10).
     * @return Список задач в формате страницы.
     */
    @Operation(summary = "Получить список задач", description = "Возвращает задачи по фильтру и с пагинацией.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список задач успешно возвращен"),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping("/tasks")
    public ResponseEntity<Page<MyTask>> getTasks(@RequestParam(required = false, defaultValue = "0") int page,
                                                         @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyTask> tasks = taskService.getTasks(pageable);
        return ResponseEntity.ok(tasks);
    }

    @Operation(summary = "Получить список задач для менеджера", description = "Возвращает задачи по фильтру и с пагинацией.")
    @GetMapping("/tasks/manager")
    public ResponseEntity<Page<MyTask>> getTasksByManagerId(@RequestParam UUID managerId,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyTask> tasks = taskService.findByManagerId(managerId, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Получить задачи по спецификации с пагинацией.
     *
     * @param taskDto DTO задачи, содержащий фильтры для поиска.
     * @param page    Страница для пагинации (по умолчанию 0).
     * @param size    Размер страницы для пагинации (по умолчанию 10).
     * @return Список задач, соответствующих фильтрам.
     */
    @Operation(summary = "Получить задачи по фильтрам", description = "Возвращает задачи на основе предоставленных фильтров и с пагинацией.")
    @PostMapping("/tasks/specification")
    public ResponseEntity<Page<MyTask>> getTasksBySpecification(@RequestBody TaskDto taskDto,
                                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<MyTask> tasks = taskService.getTasksBySpecification(taskDto, pageable);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Создать новую задачу.
     *
     * @param taskDto DTO с данными для создания новой задачи.
     * @return Созданная задача.
     */
    @Operation(summary = "Создать новую задачу", description = "Создает новую задачу в системе.")
    @PostMapping("/task/creat")
    public ResponseEntity<MyTask> creatTask(@RequestBody @Validated TaskDto taskDto) {
        return ResponseEntity.ok(taskService.creatTask(taskDto));
    }

    /**
     * Обновить задачу.
     *
     * @param taskDto DTO с обновленными данными задачи.
     * @return Обновленная задача.
     */
    @Operation(summary = "Обновить задачу", description = "Обновляет информацию о задаче.")
    @PatchMapping("/task/update")
    public ResponseEntity<MyTask> updateTask(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.updateTask(taskDto));
    }

    /**
     * Добавить исполнителя в задачу.
     *
     * @param taskDto DTO с данными для добавления исполнителя.
     * @return Обновленная задача с добавленным исполнителем.
     */
    @Operation(summary = "Добавить исполнителя", description = "Добавляет исполнителя в задачу.")
    @PatchMapping("/assignee/add")
    public ResponseEntity<MyTask> addAssignee(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.addAssignee(taskDto));
    }

    /**
     * Удалить исполнителя из задачи.
     *
     * @param taskDto DTO с данными для удаления исполнителя.
     * @return Обновленная задача с удаленным исполнителем.
     */
    @Operation(summary = "Удалить исполнителя", description = "Удаляет исполнителя из задачи.")
    @PatchMapping("/assignee/delete")
    public ResponseEntity<MyTask> deleteAssignee(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.deleteAssignee(taskDto));
    }

    /**
     * Добавить комментарий к задаче.
     *
     * @param commentDto DTO с данными комментария.
     * @return Созданный комментарий.
     */
    @Operation(summary = "Добавить комментарий", description = "Добавляет новый комментарий к задаче.")
    @PostMapping("/task/comment/creat")
    public ResponseEntity<Comment> addComment(@RequestBody @Validated CommentDto commentDto) {
        Comment comment = taskService.addComment(commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    /**
     * Удалить комментарий из задачи.
     *
     * @param commentDto DTO с данными комментария для удаления.
     * @return Обновленная задача после удаления комментария.
     */
    @Operation(summary = "Удалить комментарий", description = "Удаляет комментарий из задачи.")
    @PatchMapping("/task/comment/delete")
    public ResponseEntity<MyTask> deleteComment(@RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(taskService.deleteComment(commentDto));
    }

    /**
     * Удалить задачу.
     *
     * @param taskDto DTO с данными задачи для удаления.
     * @return Ответ с кодом состояния 200 при успешном удалении.
     */
    @Operation(summary = "Удалить задачу", description = "Удаляет задачу из системы.")
    @PatchMapping("/task/delete")
    public ResponseEntity<Void> deleteTask(@RequestBody TaskDto taskDto) {
        taskService.deleteTask(taskDto);
        return ResponseEntity.ok().build();
    }

}
