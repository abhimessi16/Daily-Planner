package com.abhimessi.dailyplanner.service;

import com.abhimessi.dailyplanner.model.Task;
import com.abhimessi.dailyplanner.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public void addTask(Task task){
        taskRepository.save(task);
    }

    public Task getTask(String taskName){
        return taskRepository.findByTaskName(taskName);
    }

    public Optional<Task> getTaskById(Integer id) { return taskRepository.findById(id); }

    public void deleteTask(String taskName){
        taskRepository.deleteByTaskName(taskName);
    }

    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }

}
