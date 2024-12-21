package com.thirdeye.scheduler.config;

import com.thirdeye.scheduler.interceptors.AuthorizationInterceptor;
import com.thirdeye.scheduler.utils.AllMicroservicesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
    private AuthorizationInterceptor authorizationInterceptor;
    
    @Autowired
	AllMicroservicesData allMicroservicesData;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/api/statuschecker/"+allMicroservicesData.current.getMicroserviceUniqueId()+"/getStatus");
    }
}

