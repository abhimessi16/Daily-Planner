package com.abhimessi.dailyplanner.repository;

import com.abhimessi.dailyplanner.model.Planner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlannerRepository extends JpaRepository<Planner, Long> {
    Optional<Planner> findByUsername(String username);
}
