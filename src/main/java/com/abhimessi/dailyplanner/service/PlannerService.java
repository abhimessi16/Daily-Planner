package com.abhimessi.dailyplanner.service;

import com.abhimessi.dailyplanner.model.*;
import com.abhimessi.dailyplanner.repository.PlannerRepository;
import com.abhimessi.dailyplanner.repository.TaskRepository;
import com.abhimessi.dailyplanner.repository.TimeSlotRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;
    private final TaskRepository taskRepository;
    private final TimeSlotRepository timeSlotRepository;

    public Planner addNewPlanner(PlannerBody plannerBody){

        int startTime = Integer.parseInt(plannerBody.getStartTime());
        int endTime = Integer.parseInt(plannerBody.getEndTime());

        Planner planner = Planner.builder()
                .username(plannerBody.getUsername())
                .password("")
                .startTime(startTime)
                .endTime(endTime)
                .timeSlots(new ArrayList<TimeSlot>())
                .build();

        for(int time = startTime; time < endTime; time++){

            Task task = new Task();
            task.setTaskName("First Task!");
            task.setTaskDescription("This is a test task to check view component");
            task.setTaskStatus(TaskStatus.PENDING);

            TimeSlot timeSlot = new TimeSlot();
            timeSlot.setStartTime(startTime++);
            timeSlot.setEndTime(startTime);
            timeSlot.setTasks(new ArrayList<>());

            timeSlot.getTasks().add(task);
            planner.getTimeSlots().add(timeSlot);
        }

        return plannerRepository.save(planner);
    }

    public List<String> getAllPlanners(){

        return plannerRepository.findAll()
                .stream().map(Planner::getUsername)
                .toList();

    }

    public Optional<Planner> getPlanner(String username){
        return plannerRepository.findByUsername(username);
    }

    public List<Task> getAllTasks(Planner planner){
        List<Task> allTasks = new ArrayList<>();

        planner.getTimeSlots().forEach(timeSlot -> allTasks.addAll(timeSlot.getTasks()));

        return allTasks;
    }

    public List<TimeSlot> getAllTimeSlots(Planner planner){
        return planner.getTimeSlots();
    }

    public List<Task> getTimeSlotTasks(Planner planner, Integer timeSlot){
        return planner.getTimeSlots().get(timeSlot).getTasks();
    }

    public void addTaskToPlanner(Planner planner, Task taskToAdd, int timeSlotId) {

        taskToAdd = taskRepository.save(taskToAdd);

        TimeSlot timeSlot = planner.getTimeSlots().get(timeSlotId);
        timeSlot.getTasks().add(taskToAdd);

        planner.getTimeSlots().set(timeSlotId, timeSlot);

        timeSlotRepository.save(timeSlot);

    }
}
