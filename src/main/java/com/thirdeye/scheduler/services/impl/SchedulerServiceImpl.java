package com.thirdeye.scheduler.services.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.thirdeye.scheduler.externalcontrollers.Thirdeye_Guider_Connection;
import com.thirdeye.scheduler.services.SchedulerService;

@Service
public class SchedulerServiceImpl implements SchedulerService {
	
	@Autowired
	Thirdeye_Guider_Connection thirdeye_Guider_Connection;
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	
	
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Kolkata")
    public void restartAllMicroservices() {
		logger.info("Going to restart all microservices.");
		Map<String,Boolean> status = thirdeye_Guider_Connection.restartAllMicroservices();
		
		if(status == null)
		{
			logger.error("Failed to start any microservices");
		}
		else
		{
			status.forEach((key, value) -> {
				logger.info("Status of restarting microservice {} is {}", key, value);
			});
		}
    }
}
