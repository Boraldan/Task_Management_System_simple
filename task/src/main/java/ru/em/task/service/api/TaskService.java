package ru.em.task.service.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.em.entity.task.Comment;
import ru.em.entity.task.MyTask;
import ru.em.entity.task.dto.CommentDto;
import ru.em.entity.task.dto.TaskDto;

import java.util.UUID;

public interface TaskService {

    /**
     *
     * @param pageable Объект пагинации, содержащий номер страницы и размер страницы.
     * @return Список всех задач.
     */


    Page<MyTask> getTasks(Pageable pageable);

    /**
     * Получить задачи, назначенные конкретному менеджеру, с пагинацией.
     *
     * @param managerId Идентификатор
     * @param pageable  Объект пагинации, содержащий номер страницы и размер страницы.
     * @return Страница задач, назначенных менеджеру.
     */

    Page<MyTask> findByManagerId(UUID managerId, Pageable pageable);

    /**
     * Получить задачи, назначенные конкретному пользователю, с пагинацией.
     *
     * @param userId   Идентификатор пользователя.
     * @param pageable Объект пагинации, содержащий номер страницы и размер страницы.
     * @return Страница задач, назначенных пользователю.
     */
    Page<MyTask> getTasksByUserId(UUID userId, Pageable pageable);

    /**
     * Создать новую задачу.
     *
     * @param taskDto Объект DTO с данными для создания задачи.
     * @return Созданная задача.
     */
    MyTask creatTask(TaskDto taskDto);

    /**
     * Обновить данные существующей задачи.
     *
     * @param taskDto Объект DTO с обновленными данными задачи.
     * @return Обновленная задача.
     */
    MyTask updateTask(TaskDto taskDto);

    /**
     * Удалить задачу.
     *
     * @param taskDto Объект DTO с данными задачи для удаления.
     */
    void deleteTask(TaskDto taskDto);

    /**
     * Добавить комментарий к задаче.
     *
     * @param commentDto Объект DTO с данными комментария.
     * @return Созданный комментарий.
     */
    Comment addComment(CommentDto commentDto);

    /**
     * Добавить исполнителя к задаче.
     *
     * @param taskDto Объект DTO с данными задачи для добавления исполнителя.
     * @return Задача с обновленным списком исполнителей.
     */
    MyTask addAssignee(TaskDto taskDto);

    /**
     * Удалить исполнителя из задачи.
     *
     * @param taskDto Объект DTO с данными задачи для удаления исполнителя.
     * @return Задача с обновленным списком исполнителей.
     */
    MyTask deleteAssignee(TaskDto taskDto);

    /**
     * Удалить комментарий из задачи.
     *
     * @param commentDto Объект DTO с данными комментария для удаления.
     * @return Задача после удаления комментария.
     */
    MyTask deleteComment(CommentDto commentDto);

    /**
     * Получить задачи на основе предоставленных фильтров и с пагинацией.
     *
     * @param taskDto  Объект DTO с фильтрами для поиска задач.
     * @param pageable Объект пагинации, содержащий номер страницы и размер страницы.
     * @return Страница задач, соответствующих фильтрам.
     */
    Page<MyTask> getTasksBySpecification(TaskDto taskDto, Pageable pageable);
}
