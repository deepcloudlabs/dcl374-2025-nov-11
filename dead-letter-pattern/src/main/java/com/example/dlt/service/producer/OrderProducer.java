package com.example.dlt.service.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {
	private final KafkaTemplate<String, String> template;

	public OrderProducer(KafkaTemplate<String, String> template) {
		this.template = template;
	}

	public void send(String key, String payload) {
		template.send("orders", key, payload);
	}
}
