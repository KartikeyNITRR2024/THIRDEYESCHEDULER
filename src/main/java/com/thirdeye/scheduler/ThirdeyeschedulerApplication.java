package com.thirdeye.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThirdeyeschedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThirdeyeschedulerApplication.class, args);
	}

}
