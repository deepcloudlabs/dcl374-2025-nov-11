package com.example.crm.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CrmRestConsumerService {

	private final RestTemplate restTemplate;

	public CrmRestConsumerService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Scheduled(fixedRate = 5_000)
	public void sendHttpRequest() {
		var response = restTemplate.getForEntity("http://localhost:5050/crm/api/v1/customers/11111111110",String.class).getBody();
		System.out.println("[Server-side Load Balancing] RestTemplate has received the response: %s".formatted(response));
	}
	
}
