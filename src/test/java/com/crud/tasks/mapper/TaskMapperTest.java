package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper = new TaskMapper();

    @Test
    public void mapToTaskTest() {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Task1", "Content1");
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(1L, (long)task.getId());
        assertEquals("Task1", task.getTitle());
        assertEquals("Content1", task.getContent());
    }

    @Test
    public void mapToTaskDtoTest() {
        //Given
        Task task = new Task(1L, "Task1", "Content1");
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(1L, (long)taskDto.getId());
        assertEquals("Task1", taskDto.getTitle());
        assertEquals("Content1", taskDto.getContent());
    }

    @Test
    public void mapToTaskDtoListTest() {
        //Given
        Task task1 = new Task(1L, "Task1", "Content1");
        Task task2 = new Task(2L, "Task2", "Content2");
        List<Task> tasks = new ArrayList<>(Arrays.asList(task1, task2));
        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        assertEquals(2, taskDtos.size());
        assertEquals(1L, (long)taskDtos.get(0).getId());
        assertEquals("Task1", taskDtos.get(0).getTitle());
        assertEquals("Content1", taskDtos.get(0).getContent());
    }

}
