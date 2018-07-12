package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void shouldFetchEmptyTaskList() throws Exception {
        //Given
        List<TaskDto> taskDtoList = new ArrayList<>();

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTaskList() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");
        List<TaskDto> taskDtoList = new ArrayList<>(Arrays.asList(taskDto));

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(get("/v1/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test title")))
                .andExpect(jsonPath("[0].content", is("Test content")));
    }

    @Test
    public void shouldFetchTaskById() throws Exception {
        //Given
        Task task = new Task(1L, "Test title", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");

        when(service.getTaskById(anyLong())).thenReturn(Optional.ofNullable(task));
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Test title")))
                .andExpect(jsonPath("$.content", is("Test content")));
    }

    @Test
    public void shouldThrowTaskNotFoundException() throws Exception {
        //Given
        Task task = new Task(1L, "Test title", "Test content");
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");

        when(service.getTaskById(anyLong())).thenReturn(Optional.empty());
        //When & Then
        mockMvc.perform(get("/v1/tasks/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(404));
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Updated title", "Updated content");

        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class)))))
                .thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Updated title")))
                .andExpect(jsonPath("$.content", is("Updated content")));
    }

    @Test
    public void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");
        Task task = new Task(1L, "Test title", "Test content");

        when(service.saveTask(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class)))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

}