package com.example.ob.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ob.application.OrderApplication;
import com.example.ob.dto.response.AcceptOrderResponse;
import com.example.ob.entity.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class OrderService {
	private final OrderApplication orderApplication;

	public OrderService(OrderApplication orderApplication) {
		this.orderApplication = orderApplication;
	}

	// ACL: Anti-Corruption Layer
	@Transactional
	public AcceptOrderResponse acceptOrder(OrderEntity order) {
		try {
			orderApplication.accept(order);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e.getMessage());
		}
		return new AcceptOrderResponse("success");
	}

}
