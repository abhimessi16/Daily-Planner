package com.abhimessi.dailyplanner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;
    private String taskName;
    private int taskFinishTime;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

}