package com.abhimessi.dailyplanner.controller;

import com.abhimessi.dailyplanner.model.Planner;
import com.abhimessi.dailyplanner.model.PlannerBody;
import com.abhimessi.dailyplanner.service.PlannerService;
import com.abhimessi.dailyplanner.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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



}
