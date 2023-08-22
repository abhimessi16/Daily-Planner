package com.abhimessi.dailyplanner.model.RequestBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TaskAddBody {

    private String taskName;
    private String taskDescription;
    private String user;
    private Integer startTime;

}
