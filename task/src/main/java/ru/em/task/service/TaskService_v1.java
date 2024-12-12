package ru.em.task.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.em.entity.task.Comment;
import ru.em.entity.task.MyTask;
import ru.em.entity.task.dto.CommentDto;
import ru.em.entity.task.dto.TaskDto;
import ru.em.task.controller.exception.ResourceNotFoundException;
import ru.em.task.repo.TaskRepository;
import ru.em.task.repo.TaskSpecification;
import ru.em.task.service.api.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService_v1 implements TaskService {

    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public Page<MyTask> getTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    public Page<MyTask> findByManagerId(UUID managerId, Pageable pageable) {
        return taskRepository.findByManagerId(managerId, pageable);
    }

    public Page<MyTask> getTasksByUserId(UUID userId, Pageable pageable) {
        return taskRepository.findByUsersListContaining(userId, pageable);
    }

    @Transactional
    public MyTask creatTask(TaskDto taskDto) {
        return taskRepository.save(mapFromMyTaskDtoToMyTask(taskDto));
    }

    @Transactional
    public MyTask updateTask(TaskDto taskDto) {
        MyTask task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskDto.getTaskId()));

        if (taskDto.getTitle() != null) {
            task.setTitle(taskDto.getTitle());
        }
        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }
        if (taskDto.getPriority() != null) {
            task.setPriority(taskDto.getPriority());
        }
        if (taskDto.getManagerId() != null) {
            task.setManagerId(taskDto.getManagerId());
        }
        if (taskDto.getUsersList() != null) {
            task.setUsersList(taskDto.getUsersList());
        }
        if (taskDto.getComments() != null) {
            task.setComments(taskDto.getComments());
        }
        if (taskDto.getIsActive() != null) {
            task.setIsActive(taskDto.getIsActive());
        }

        return taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(TaskDto taskDto) {
        MyTask task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskDto.getTaskId()));
        taskRepository.delete(task);
    }

    @Transactional
    public Comment addComment(CommentDto commentDto) {
        MyTask task = taskRepository.findById(commentDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + commentDto.getTaskId()));

        Comment comment = new Comment();
        comment.setTask(task);
        comment.setUserId(commentDto.getUserId());
        comment.setContent(commentDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        task.getComments().add(comment);

        taskRepository.save(task);
        return comment;
    }

    @Transactional
    public MyTask addAssignee(TaskDto taskDto) {
        MyTask task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskDto.getTaskId()));

        task.getUsersList().addAll(task.getUsersList());

        return task;
    }

    @Transactional
    public MyTask deleteAssignee(TaskDto taskDto) {
        MyTask task = taskRepository.findById(taskDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + taskDto.getTaskId()));

        task.getUsersList().removeAll(task.getUsersList());

        return task;
    }

    @Transactional
    public MyTask deleteComment(CommentDto commentDto) {
        MyTask task = taskRepository.findById(commentDto.getTaskId())
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with ID: " + commentDto.getTaskId()));

        List<Comment> commentList = task.getComments().stream().filter(comment -> comment.getCommentId() != commentDto.getCommentId()).toList();
        task.setComments(commentList);
        return taskRepository.save(task);
    }

    public Page<MyTask> getTasksBySpecification(TaskDto taskDto, Pageable pageable) {
        Specification<MyTask> specification = Specification.where(TaskSpecification.hasTaskId(taskDto.getTaskId()))
                .and(TaskSpecification.hasTitle(taskDto.getTitle()))
                .and(TaskSpecification.hasDescription(taskDto.getDescription()))
                .and(TaskSpecification.hasStatus(taskDto.getStatus()))
                .and(TaskSpecification.hasPriority(taskDto.getPriority()))
                .and(TaskSpecification.isActive(taskDto.getIsActive()))
                .and(TaskSpecification.hasManagerId(taskDto.getManagerId()));

        if (taskDto.getUsersList() != null && !taskDto.getUsersList().isEmpty()) {
            for (UUID userId : taskDto.getUsersList()) {
                specification = specification.and(TaskSpecification.hasUsersListContaining(userId));
            }
        }

        return taskRepository.findAll(specification, pageable);
    }

    private MyTask mapFromMyTaskDtoToMyTask(TaskDto taskDto) {
        return modelMapper.map(taskDto, MyTask.class);
    }
}