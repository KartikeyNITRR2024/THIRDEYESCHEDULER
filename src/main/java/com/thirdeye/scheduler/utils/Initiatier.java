package com.thirdeye.scheduler.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component 
public class Initiatier {
	
	@Autowired
	AllMicroservicesData allMicroservicesData;
	
	
	private static final Logger logger = LoggerFactory.getLogger(Initiatier.class);
	
	@PostConstruct
    public void init() throws Exception{
        logger.info("Initializing Initiatier...");
        allMicroservicesData.getAllMicroservicesData();
        logger.info("Initiatier initialized.");
    }
}
