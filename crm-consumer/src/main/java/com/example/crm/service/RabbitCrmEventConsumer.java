package com.example.crm.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitCrmEventConsumer {

	@RabbitListener(queues = "crmque")
	public void handleCrmEvent(String event) {
		System.err.println("New event has arrived from rabbit: %s".formatted(event));

	}
}
