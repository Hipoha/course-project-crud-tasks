package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    @InjectMocks
    private DbService dbService;

    @Mock
    private TaskRepository repository;

    @Test
    public void getAllTasksTest() {
        //Given
        Task task1 = new Task(1L, "Title1", "Content1");
        Task task2 = new Task(2L, "Title2", "Content2");
        List<Task> tasks = new ArrayList<>(Arrays.asList(task1, task2));

        when(repository.findAll()).thenReturn(tasks);
        //When
        List<Task> tasksResult = dbService.getAllTasks();
        //Then
        assertEquals(2, tasksResult.size());
    }

    @Test
    public void getTaskByIdValidTest() {
        //Given
        Task task = new Task(1L, "Title1", "Content1");
        Optional<Task> taskOptional = Optional.of(task);

        when(repository.findById(any(Long.class))).thenReturn(taskOptional);
        //When
        Optional<Task> taskOptionalResult = dbService.getTaskById(1L);
        //Then
        assertEquals(1L, (long)taskOptionalResult.get().getId());
        assertEquals("Title1", taskOptionalResult.get().getTitle());
        assertEquals("Content1", taskOptionalResult.get().getContent());
    }

    @Test
    public void getTaskByIdInvalidTest() {
        //Given
        Optional<Task> taskOptional = Optional.empty();

        when(repository.findById(any(Long.class))).thenReturn(taskOptional);
        //When
        Optional<Task> taskOptionalResult = dbService.getTaskById(1L);
        //Then
        assertFalse(taskOptionalResult.isPresent());
    }

    @Test
    public void saveTaskTest() {
        //Given
        Task task = new Task(1L, "Title1", "Content1");
        Task taskReturned = new Task(1L, "Title1", "Content1");

        when(repository.save(task)).thenReturn(taskReturned);
        //When
        Task taskResult = dbService.saveTask(task);
        //Then
        assertEquals(1l, (long)taskResult.getId());
        assertEquals("Title1", taskResult.getTitle());
        assertEquals("Content1", taskResult.getContent());
    }

}
