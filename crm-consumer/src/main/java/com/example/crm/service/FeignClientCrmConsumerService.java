package com.example.crm.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FeignClientCrmConsumerService {
	private final CrmService crmService;

	public FeignClientCrmConsumerService(CrmService crmService) {
		this.crmService = crmService;
	}
	
	@Scheduled(fixedRate = 3_000)
	public void sendHttpRequest() {
		System.out.println(crmService.getCustomerById("11111111110"));
	}
}
