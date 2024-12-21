package com.thirdeye.scheduler.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.thirdeye.scheduler.services.impl.UpdateInitiaterServiceImpl;
import com.thirdeye.scheduler.entity.MicroservicesInfo;
import com.thirdeye.scheduler.externalcontrollers.ThirdEye_Purse_Connection;
import com.thirdeye.scheduler.externalcontrollers.Thirdeye_Scheduler_Connection;
import com.thirdeye.scheduler.externalcontrollers.Thirdeye_Stock_Market_Viewer_Connection;
import com.thirdeye.scheduler.externalcontrollers.Thirdeye_Stockmanager_Connection;
import com.thirdeye.scheduler.services.UpdateStatus;
import com.thirdeye.scheduler.utils.AllMicroservicesData;

@Service
public class UpdateStatusImpl implements UpdateStatus {

	@Value("${statusTimeGapInSeconds}")
	private Long statusTimeGapInSeconds;
	
	@Autowired
	private AllMicroservicesData allMicroservicesData;
	
    @Autowired
    private RestTemplate restTemplate;
    
	private static final Logger logger = LoggerFactory.getLogger(UpdateStatusImpl.class);
	
//	@Override
//	public Map<String, Boolean> getAllMicroServicesStatusByApi() {
//		Map<String,Boolean> statusMap = new HashMap();
//		for (Map.Entry<String, MicroservicesInfo> entry : allMicroservicesData.allMicroservices.entrySet()) {
//		    String microserviceName = entry.getKey();
//		    MicroservicesInfo microserviceData = entry.getValue();
//		    Boolean connection = checkStatus(microserviceData);
//		    statusMap.put(microserviceName, connection);
//		}
//		return statusMap;
//	}
	
	@Override
	public Map<String, Boolean> getAllMicroServicesStatusByApi() {
        Map<String, CompletableFuture<Boolean>> futureMap = allMicroservicesData.allMicroservices.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> checkStatusAsync(entry.getValue())
            ));

        Map<String, Boolean> statusMap = new HashMap<>();
        futureMap.forEach((name, future) -> {
            try {
                statusMap.put(name, future.get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Error while retrieving status for " + name, e);
                statusMap.put(name, Boolean.FALSE); // Default to false on error
            }
        });

        return statusMap;
    }
	
//	@Override
//	public Boolean checkStatus(MicroservicesInfo microserviceData) {
//      try {
//          ResponseEntity<Boolean> response = restTemplate.getForEntity(
//        		  microserviceData.getMicroserviceUrl() + "api/statuschecker/" + microserviceData.getMicroserviceUniqueId(), 
//                  Boolean.class
//          );
//          if(response.getBody() != null && response.getBody())
//          {
//        	  return Boolean.TRUE; 
//          }
//          return Boolean.FALSE;
//      } catch (Exception e) {
//          logger.error("Error while checking status: ", e);
//          return Boolean.FALSE; 
//      }
//   }
	
	@Override
    @Async("StatusCheckerAsyncThread")
    public CompletableFuture<Boolean> checkStatusAsync(MicroservicesInfo microserviceData) {
//		logger.info("Going to check status of {}",microserviceData.getMicroserviceName());
        Boolean status;
        try {
            ResponseEntity<Boolean> response = restTemplate.getForEntity(
                microserviceData.getMicroserviceUrl() + "api/statuschecker/" + microserviceData.getMicroserviceUniqueId(), 
                Boolean.class
            );
            status = response.getBody() != null && response.getBody();
        } catch (Exception e) {
            logger.error("Error while checking status: ", e);
            status = Boolean.FALSE;
        }
        return CompletableFuture.completedFuture(status);
    }

	@Override
	@Scheduled(fixedRateString = "${statusTimeGapInSeconds}000", zone = "Asia/Kolkata")
	public void getAllMicroServicesStatus() {
		logger.info("Going to check status of all microservices.");
		getAllMicroServicesStatusByApi();
		logger.info("status check of all microservices completed.");
	}

}
