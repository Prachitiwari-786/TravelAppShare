package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CabServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CabServiceApplication.class, args);
	}
	
	@Bean 
	@LoadBalanced // ✅ critical annotation 
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
