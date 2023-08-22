package com.abhimessi.dailyplanner.controller;

import com.abhimessi.dailyplanner.model.Planner;
import com.abhimessi.dailyplanner.model.PlannerBody;
import com.abhimessi.dailyplanner.model.RequestBody.TaskAddBody;
import com.abhimessi.dailyplanner.model.Task;
import com.abhimessi.dailyplanner.model.TaskStatus;
import com.abhimessi.dailyplanner.service.PlannerService;
import com.abhimessi.dailyplanner.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class YourDayController {

    private TaskService taskService;
    private PlannerService plannerService;

    @ModelAttribute(value = "planner")
    public PlannerBody newEntity()
    {
        return new PlannerBody();
    }

    @GetMapping({"/", "/home"})
    public String yourDay(Model model){

        // all changes or updates need to be added to model again for each request
        model.addAttribute("users", plannerService.getAllPlanners());

        return "index";
    }

    @PostMapping({"/", "/home"})
    public String wholeDayTiming(@RequestBody PlannerBody planner, Model model){

        Planner presentPlanner = plannerService.addNewPlanner(planner);
        model.addAttribute("users", plannerService.getAllPlanners());

        return "index :: #user-select";
    }

    @GetMapping("/get-all-tasks")
    public String getTasksForUser(@RequestParam String user, Model model){

        Optional<Planner> optionalPlanner = plannerService.getPlanner(user);
        Planner planner = optionalPlanner.orElseThrow();

        model.addAttribute("timeSlots", plannerService.getAllTimeSlots(planner));

        return "index :: .task-container";
    }

    // have to check if making one post request
    // will remove model values from the previous
    // post request

    @GetMapping("/get-tasks")
    public String getTasks(@RequestParam Integer startTime, @RequestParam String user, Model model){

        Optional<Planner> optionalPlanner = plannerService.getPlanner(user);
        Planner planner = optionalPlanner.orElseThrow();

        model.addAttribute("tasks", plannerService.getTimeSlotTasks(planner
                , startTime - planner.getStartTime()));
        model.addAttribute("timeSlots", plannerService.getAllTimeSlots(planner));
        model.addAttribute("users", plannerService.getAllPlanners());

        plannerService.getTimeSlotTasks(planner, startTime - planner.getStartTime())
                .forEach(task -> System.out.println(task.getTaskId() + " " + task.getTaskName()));

        return "index :: #main";
    }

    @GetMapping("/update-status")
    public String updateTaskStatus(@RequestParam String user
            , @RequestParam Integer startTime
            , @RequestParam Integer taskId
            , @RequestParam String status
            , Model model){

        String newStatus = "";

        for(TaskStatus taskStatus: TaskStatus.values()){
            if(!status.equals(taskStatus.name()))
                newStatus = taskStatus.name();
        }

        Task task = taskService.getTaskById(taskId).orElseThrow();
        task.setTaskStatus(TaskStatus.valueOf(newStatus));
        taskService.addTask(task);

        Optional<Planner> optionalPlanner = plannerService.getPlanner(user);
        Planner planner = optionalPlanner.orElseThrow();

        model.addAttribute("tasks", plannerService.getTimeSlotTasks(planner
                , startTime - planner.getStartTime()));
        model.addAttribute("timeSlots", plannerService.getAllTimeSlots(planner));
        model.addAttribute("users", plannerService.getAllPlanners());

        return "index :: #main";
    }

    @GetMapping("/remove-task")
    public String removeTask(@RequestParam String user
            , @RequestParam Integer startTime
            , @RequestParam Integer taskId, Model model){

        Task taskToRemove = taskService.getTaskById(taskId).orElseThrow();

        Optional<Planner> optionalPlanner = plannerService.getPlanner(user);
        Planner planner = optionalPlanner.orElseThrow();

        plannerService.getTimeSlotTasks(planner
                , startTime - planner.getStartTime()
        ).remove(taskToRemove);

        taskService.deleteTaskById(taskId);

        model.addAttribute("tasks", plannerService.getTimeSlotTasks(planner
                , startTime - planner.getStartTime()));
        model.addAttribute("timeSlots", plannerService.getAllTimeSlots(planner));
        model.addAttribute("users", plannerService.getAllPlanners());

        return "index :: #main";
    }

    @PostMapping("/task-add")
    public String addTask(@RequestBody TaskAddBody taskAddBody, Model model){

        Optional<Planner> optionalPlanner = plannerService.getPlanner(taskAddBody.getUser());
        Planner planner = optionalPlanner.orElseThrow();

        Task taskToAdd = new Task();
        taskToAdd.setTaskName(taskAddBody.getTaskName());
        taskToAdd.setTaskDescription(taskAddBody.getTaskDescription());
        taskToAdd.setTaskStatus(TaskStatus.PENDING);

        plannerService.addTaskToPlanner(planner, taskToAdd, taskAddBody.getStartTime() - planner.getStartTime());

        model.addAttribute("tasks", plannerService.getTimeSlotTasks(planner
                , taskAddBody.getStartTime() - planner.getStartTime()));
        model.addAttribute("timeSlots", plannerService.getAllTimeSlots(planner));
        model.addAttribute("users", plannerService.getAllPlanners());

        return "index :: #main";
    }

}
