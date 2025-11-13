package com.example.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {
	"com.example.crm.service"
})
public class CrmConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmConsumerApplication.class, args);
	}

}
