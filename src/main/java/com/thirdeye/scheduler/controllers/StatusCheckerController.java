package com.thirdeye.scheduler.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thirdeye.scheduler.annotation.AdminRequired;
import com.thirdeye.scheduler.services.UpdateStatus;
import com.thirdeye.scheduler.utils.AllMicroservicesData;

@RestController
@RequestMapping("/api/statuschecker")
public class StatusCheckerController {
	
	@Autowired
	AllMicroservicesData allMicroservicesData;
	
	@Autowired
    private UpdateStatus updateStatus;
    
	private static final Logger logger = LoggerFactory.getLogger(StatusCheckerController.class);
    
    @GetMapping("/{uniqueId}/getStatus")
    @AdminRequired
    public ResponseEntity<Map> getAllMicroservicesStatus(@PathVariable("uniqueId") Integer pathUniqueId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
        	Map<String, Boolean> responseData = this.updateStatus.getAllMicroServicesStatusByApi();
            return ResponseEntity.ok(responseData);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
	
    

    @GetMapping("/{uniqueId}")
    public ResponseEntity<Boolean> getStatus(@PathVariable("uniqueId") Integer pathUniqueId) {
        if (pathUniqueId.equals(allMicroservicesData.current.getMicroserviceUniqueId())) {
            logger.info("Status check for uniqueId {}: Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.ok(true);
        } else {
            logger.warn("Status check for uniqueId {}: Not Found", allMicroservicesData.current.getMicroserviceUniqueId());
            return ResponseEntity.notFound().build();
        }
    }
}
