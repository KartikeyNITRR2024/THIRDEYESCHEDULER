package com.thirdeye.scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
	
	@Value("${maximumResponseWaitTime}")
	private Integer maximumResponseWaitTime;

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(maximumResponseWaitTime * 1000);
        requestFactory.setReadTimeout(maximumResponseWaitTime * 1000);
        return new RestTemplate(requestFactory);
    }
}

