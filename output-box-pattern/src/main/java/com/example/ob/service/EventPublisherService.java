package com.example.ob.service;

import java.time.Instant;

import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ob.entity.OutboxEventStatus;
import com.example.ob.repository.OutboxEventRepository;

@Service
public class EventPublisherService {
	private final OutboxEventRepository outboxEventRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;

	public EventPublisherService(OutboxEventRepository outboxEventRepository,
			KafkaTemplate<String, String> kafkaTemplate) {
		this.outboxEventRepository = outboxEventRepository;
		this.kafkaTemplate = kafkaTemplate;
	}

	@Transactional
	public void publishBulk() {
		System.err.println("publishBulk() is just started!");
		outboxEventRepository.findByStatusOrderByCreatedAtDesc(PageRequest.of(0, 10), OutboxEventStatus.NEW)
				.forEach(outboxEvent -> {
					try {
						kafkaTemplate.send(outboxEvent.getTopic(), outboxEvent.getPayload());
						outboxEvent.setStatus(OutboxEventStatus.PUBLISHED);
						outboxEvent.setPublishedAt(Instant.now());
					} catch (Exception e) {
						outboxEvent.setStatus(OutboxEventStatus.FAILED);
						System.err.println(e.getMessage());
					}
				});
		System.err.println("about to leave publishBulk()");
	}

}
