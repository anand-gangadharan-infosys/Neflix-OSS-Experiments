package com.anand.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.anand.pin.client.PinClient;

@SpringBootApplication
@EnableEurekaClient
public class Application {
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	PinClient pinClient() {
		return new PinClient();
	}
	
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}