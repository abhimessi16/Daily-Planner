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
    private Integer taskId;
    private String taskName;
    private String taskDescription;

    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

}
