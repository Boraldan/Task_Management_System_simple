package ru.em.task;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.em.entity.task.MyTask;
import ru.em.entity.task.Priority;
import ru.em.entity.task.Status;
import ru.em.entity.task.dto.TaskDto;
import ru.em.task.controller.ManagerTaskController;
import ru.em.task.service.api.TaskService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(ManagerTaskController.class)
class ManagerTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private ManagerTaskController managerTaskController;

    @Autowired
    private ObjectMapper objectMapper;

    private MyTask sampleTask;
    private Page<MyTask> taskPage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleTask = new MyTask();
        sampleTask.setTaskId(UUID.randomUUID());
        sampleTask.setTitle("Test Task");
        sampleTask.setDescription("Test Description");

        List<MyTask> tasks = Arrays.asList(sampleTask);
        taskPage = new PageImpl<>(tasks, PageRequest.of(0, 10), 1);
    }

    @Test
    void testGetTasks() throws Exception {
        when(taskService.getTasks(any(Pageable.class))).thenReturn(taskPage);

        mockMvc.perform(get("/api/manager/tasks")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));

        verify(taskService, times(1)).getTasks(any(Pageable.class));
    }

    @Test
    void testGetTasksByManagerId() throws Exception {
        UUID managerId = UUID.fromString("33333333-3333-3333-3333-333333333333");
        when(taskService.findByManagerId(eq(managerId), any(Pageable.class))).thenReturn(taskPage);

        mockMvc.perform(get("/api/manager/tasks/manager")
                        .param("managerId", managerId.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Test Task"));

        verify(taskService, times(1)).findByManagerId(eq(managerId), any(Pageable.class));
    }

    @Test
    void shouldCreateTask() throws Exception {
        // Arrange
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("New Task");
        taskDto.setDescription("Task Description");
        taskDto.setPriority(Priority.HIGH);
        taskDto.setStatus(Status.PENDING);
        taskDto.setManagerId(UUID.randomUUID());
        taskDto.setUsersList(List.of(UUID.randomUUID()));

        MyTask myTask = new MyTask();
        myTask.setTitle("New Task");
        myTask.setDescription("Task Description");

        when(taskService.creatTask(any(TaskDto.class))).thenReturn(myTask);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/manager/task/creat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("New Task"));
    }

}
