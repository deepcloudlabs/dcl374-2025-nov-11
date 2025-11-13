package com.example.crm.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.crm.service.business.event.CrmBaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CrmEventRabbitPublisherService implements CrmEventHandler {

	private final RabbitTemplate rabbitTemplate;
	private final ObjectMapper objectMapper;

	public CrmEventRabbitPublisherService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
		this.rabbitTemplate = rabbitTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	@EventListener
	@Async
	public void listenCrmEvents(CrmBaseEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			rabbitTemplate.convertAndSend("crmex", null,eventAsJson);
		} catch (JsonProcessingException e) {
			System.err.println("Error has occured while convert to JSON: %s".formatted(e.getMessage()));
		}

	}

}
