package com.example.crm.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaCrmEventConsumer {

	@KafkaListener(topics = "crm-events", groupId = "crm-consumer")
	public void handleCrmEvent(String event) {
		System.err.println("New event has arrived from kafka: %s".formatted(event));
	}
}
