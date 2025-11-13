package com.example.ob.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PollingPublisherService {
	private final EventPublisherService eventPublisherService;

	public PollingPublisherService(EventPublisherService eventPublisherService) {
		this.eventPublisherService = eventPublisherService;
	}

	@Scheduled(fixedRateString = "${outbox.loop-rate:10000}")
	public void publishLoop() {
		eventPublisherService.publishBulk();
	}

}
