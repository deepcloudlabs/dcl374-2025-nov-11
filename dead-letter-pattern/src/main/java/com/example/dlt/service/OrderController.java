package com.example.dlt.service;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.dlt.service.producer.OrderProducer;

@Service
public class OrderController {
	private final OrderProducer orderProducer;
	public OrderController(OrderProducer orderProducer) {
		this.orderProducer = orderProducer;
	}

	@Scheduled(fixedRate = 5_000)
	public void sendMessage() {
		orderProducer.send("1", "1234567891%d".formatted(ThreadLocalRandom.current().nextInt()));
	}
}
