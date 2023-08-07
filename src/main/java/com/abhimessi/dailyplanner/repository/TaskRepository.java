package com.abhimessi.dailyplanner.repository;

import com.abhimessi.dailyplanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Task findByTaskName(String taskName);
    void deleteByTaskName(String taskName);
    List<Task> findByTaskFinishTime(int time);

}
