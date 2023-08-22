package com.abhimessi.dailyplanner.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Planner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Integer startTime;
    private Integer endTime;
    private String password;

    @OneToMany
    @Cascade(CascadeType.PERSIST)
    private List<TimeSlot> timeSlots;

}
