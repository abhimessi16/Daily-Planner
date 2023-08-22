package com.abhimessi.dailyplanner.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Getter
@Setter
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer startTime;
    private Integer endTime;

    // this might change
    // for instannce let's day that there is a task
    // which is to be comlpeted in 4 hours then it must span all the four timeslots
    // i suppose that is how it should be
    // but we're not displaying all tasks available in a timeslot on the card itself
    // like if there are more tasks then we have to hide a few
    // so lets think about this
    // how it should work and all
    // if the above thought is valid then this relation
    // will become many-to-many
    @OneToMany
    @Cascade(CascadeType.PERSIST)
    private List<Task> tasks;

    public Long getCompletedCount(){
        return tasks.stream().filter(task -> task.getTaskStatus() == TaskStatus.COMPLETED).count();
    }

}
