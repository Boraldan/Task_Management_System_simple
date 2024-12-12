package ru.em.task.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.em.entity.task.MyTask;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<MyTask, UUID> {
    Page<MyTask> findByManagerId(UUID managerId, Pageable pageable);

    Optional<MyTask> findByTaskIdAndUsersListContaining(UUID taskId, UUID userId);

    Page<MyTask> findByUsersListContaining(UUID userId, Pageable pageable);

    Page<MyTask> findAll(Specification<MyTask> specification, Pageable pageable);
}