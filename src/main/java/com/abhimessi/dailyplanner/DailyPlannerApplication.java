package com.abhimessi.dailyplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DailyPlannerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DailyPlannerApplication.class, args);
	}

}
