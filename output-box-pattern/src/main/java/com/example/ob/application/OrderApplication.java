package com.example.ob.application;

import java.time.Instant;

import org.apache.kafka.common.Uuid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ob.application.event.OrderAcceptedEvent;
import com.example.ob.entity.OrderEntity;
import com.example.ob.entity.OutboxEvent;
import com.example.ob.entity.OutboxEventStatus;
import com.example.ob.repository.OrderEntityRepository;
import com.example.ob.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderApplication {
	private final OrderEntityRepository orderEntityRepository;
	private final OutboxEventRepository outboxEventRepository;
	private final ObjectMapper objectMapper;
	
	public OrderApplication(OrderEntityRepository orderEntityRepository, OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
		this.orderEntityRepository = orderEntityRepository;
		this.outboxEventRepository = outboxEventRepository;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public void accept(OrderEntity order) throws JsonProcessingException {
			orderEntityRepository.save(order);
			//orderEntityRepository.flush();
			var orderAcceptedEvent = new OrderAcceptedEvent(Uuid.randomUuid().toString(),"ORDER_ACCEPTED",order);
			outboxEventRepository.save(createOutboxEvent(orderAcceptedEvent));
	}

	private OutboxEvent createOutboxEvent(OrderAcceptedEvent orderAcceptedEvent) throws JsonProcessingException {
		var orderAcceptedEventAsJson = objectMapper.writeValueAsString(orderAcceptedEvent);
		var outboxEvent = new OutboxEvent();
		outboxEvent.setId(orderAcceptedEvent.eventId());
		outboxEvent.setCreatedAt(Instant.now());
		outboxEvent.setPayload(orderAcceptedEventAsJson);
		outboxEvent.setEventType(orderAcceptedEvent.eventType());
		outboxEvent.setTopic("orders");
		outboxEvent.setStatus(OutboxEventStatus.NEW);
		return outboxEvent;
	}
}
