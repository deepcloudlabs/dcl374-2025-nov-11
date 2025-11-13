package com.example.dlt.service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

	@KafkaListener(topics = "orders", groupId = "orders-consumers")
	public void onMessage(String order) {
		System.err.println("Received new order: %s".formatted(order));
		if (order.length() > 10) {
			throw new RuntimeException("Processing failed for order: %s".formatted(order));
		}
	}
}