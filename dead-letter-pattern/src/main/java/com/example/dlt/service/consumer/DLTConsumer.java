package com.example.dlt.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DLTConsumer {

	@KafkaListener(topics = "orders.DLT", groupId = "orders-dlt-consumers")
	public void onDlt(String message) {
		System.err.println("Dead Letter Message: %s".formatted(message));
	}
}