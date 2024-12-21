package com.thirdeye.scheduler.services;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.thirdeye.scheduler.entity.MicroservicesInfo;

public interface UpdateStatus {
	Map<String,Boolean> getAllMicroServicesStatusByApi();
	void getAllMicroServicesStatus();
//	Boolean checkStatus(MicroservicesInfo microserviceData);
	CompletableFuture<Boolean> checkStatusAsync(MicroservicesInfo microserviceData);
}
