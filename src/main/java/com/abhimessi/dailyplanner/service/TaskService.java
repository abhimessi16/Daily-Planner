package com.abhimessi.dailyplanner.service;

import com.abhimessi.dailyplanner.model.Task;
import com.abhimessi.dailyplanner.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteTask(String taskName){
        taskRepository.deleteByTaskName(taskName);
    }

    public List<Task> getTasksWithFinishTime(int finishTime){
        return taskRepository.findByTaskFinishTime(finishTime);
    }

}
