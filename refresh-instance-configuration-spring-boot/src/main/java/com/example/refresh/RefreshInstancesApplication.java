package com.example.refresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RefreshInstancesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RefreshInstancesApplication.class, args);
	}

}
