package com.abhimessi.dailyplanner.controller;

import com.abhimessi.dailyplanner.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class YourDayController {

    private TaskService taskService;

    @GetMapping({"/", "/home"})
    public String yourDay(){
        return "index";
    }

}
