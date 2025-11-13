package com.example.integration.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
@EnableScheduling
public class RestConfig {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	WebSocketClient createWebSocketClient() {
		return new StandardWebSocketClient();
	}
}
