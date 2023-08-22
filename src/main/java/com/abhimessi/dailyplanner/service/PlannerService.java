package com.abhimessi.dailyplanner.service;

import com.abhimessi.dailyplanner.model.Planner;
import com.abhimessi.dailyplanner.model.PlannerBody;
import com.abhimessi.dailyplanner.model.Task;
import com.abhimessi.dailyplanner.repository.PlannerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlannerService {

    private final PlannerRepository plannerRepository;

    public Planner addNewPlanner(PlannerBody plannerBody){
        Planner planner = Planner.builder()
                .username(plannerBody.getUsername())
                .password("")
                .startTime(Integer.valueOf(plannerBody.getStartTime()))
                .endTime(Integer.valueOf(plannerBody.getEndTime()))
                .tasks(new ArrayList<Task>())
                .build();
        return plannerRepository.save(planner);
    }

    public List<String> getAllPlanners(){

        return plannerRepository.findAll()
                .stream().map(Planner::getUsername)
                .toList();

    }

}
