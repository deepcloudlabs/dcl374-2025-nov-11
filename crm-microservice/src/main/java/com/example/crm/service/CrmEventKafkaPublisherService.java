package com.example.crm.service;

import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.crm.service.business.event.CrmBaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CrmEventKafkaPublisherService implements CrmEventHandler {

	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public CrmEventKafkaPublisherService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@Override
	@EventListener
	@Async
	public void listenCrmEvents(CrmBaseEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			kafkaTemplate.send("crm-events", eventAsJson);
		} catch (JsonProcessingException e) {
			System.err.println("Error has occured while convert to JSON: %s".formatted(e.getMessage()));
		}

	}

}
