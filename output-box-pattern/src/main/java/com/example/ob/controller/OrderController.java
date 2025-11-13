package com.example.ob.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.ob.dto.response.AcceptOrderResponse;
import com.example.ob.entity.OrderEntity;
import com.example.ob.service.OrderService;

@RestController
@RequestMapping("orders")
@RequestScope
@CrossOrigin
@Validated
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public AcceptOrderResponse acceptOrder(@RequestBody @Validated OrderEntity order) {
		return orderService.acceptOrder(order);
	}
}
