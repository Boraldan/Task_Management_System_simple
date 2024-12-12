package ru.em.task.repo;

import org.springframework.data.jpa.domain.Specification;
import ru.em.entity.task.MyTask;
import ru.em.entity.task.Priority;
import ru.em.entity.task.Status;

import java.util.UUID;

public class TaskSpecification {

    public static Specification<MyTask> hasTaskId(UUID taskId) {
        return (root, query, criteriaBuilder) ->
                taskId != null ? criteriaBuilder.equal(root.get("taskId"), taskId) : null;
    }

    public static Specification<MyTask> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title != null ? criteriaBuilder.like(root.get("title"), "%" + title + "%") : null;
    }

    public static Specification<MyTask> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                description != null ? criteriaBuilder.like(root.get("description"), "%" + description + "%") : null;
    }

    public static Specification<MyTask> hasStatus(Status status) {
        return (root, query, criteriaBuilder) ->
                status != null ? criteriaBuilder.equal(root.get("status"), status) : null;
    }

    public static Specification<MyTask> hasPriority(Priority priority) {
        return (root, query, criteriaBuilder) ->
                priority != null ? criteriaBuilder.equal(root.get("priority"), priority) : null;
    }

    public static Specification<MyTask> isActive(Boolean isActive) {
        return (root, query, criteriaBuilder) ->
                isActive != null ? criteriaBuilder.equal(root.get("isActive"), isActive) : null;
    }

    public static Specification<MyTask> hasManagerId(UUID managerId) {
        return (root, query, criteriaBuilder) ->
                managerId != null ? criteriaBuilder.equal(root.get("managerId"), managerId) : null;
    }

    public static Specification<MyTask> hasUsersListContaining(UUID userId) {
        return (root, query, criteriaBuilder) ->
                userId != null ? criteriaBuilder.isMember(userId, root.get("usersList")) : null;
    }
}
