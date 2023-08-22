package com.abhimessi.dailyplanner.repository;

import com.abhimessi.dailyplanner.model.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {
}
