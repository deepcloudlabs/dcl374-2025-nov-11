package com.example.om.service;

import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.om.dto.message.CancelInventoryMessage;
import com.example.om.dto.message.CancelPayment;
import com.example.om.dto.message.InventoryItem;
import com.example.om.dto.message.InventoryMessage;
import com.example.om.dto.message.InventoryResponseMessage;
import com.example.om.dto.message.InventoryStatus;
import com.example.om.dto.message.Payment;
import com.example.om.dto.message.PaymentResponseMessage;
import com.example.om.entity.Order;
import com.example.om.entity.OrderStatus;
import com.example.om.repository.OrderRepository;
import com.example.om.saga.Compansation;
import com.example.om.saga.OrderAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SagaCoordinator {
	private final Logger logger = LoggerFactory.getLogger(SagaCoordinator.class);

	private final OrderRepository orderRepository;
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper objectMapper;

	public SagaCoordinator(OrderRepository orderRepository, KafkaTemplate<String, String> kafkaTemplate,
			ObjectMapper objectMapper) {
		this.orderRepository = orderRepository;
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public Order createOrder(Order order) throws Exception {
		order.setStatus(OrderStatus.CREATED);
		var savedOrder = orderRepository.save(order);
		var payment = new Payment(order.getCustomerId(), order.getOrderId(), order.getTotal());
		kafkaTemplate.send("order-payment", objectMapper.writeValueAsString(payment));
		return savedOrder;
	}

	@Transactional
	@KafkaListener(topics = "order-payment-response")
	public void listenPaymentResponseMessage(String paymentResponseMesssage) throws Exception {
		var responseMessage = objectMapper.readValue(paymentResponseMesssage, PaymentResponseMessage.class);
		orderRepository.findById(responseMessage.orderId()).ifPresent(order -> {
			if (order.getStatus() == OrderStatus.CREATED) {
				try {
					order.setStatus(OrderStatus.PAYMENT);
					orderRepository.save(order);
					List<InventoryItem> items = order.getItems().stream()
							.map(item -> new InventoryItem(item.getSku(), item.getQuantity())).toList();
					var inventoryMessage = new InventoryMessage(order.getOrderId(), items);
					kafkaTemplate.send("order-inventory", objectMapper.writeValueAsString(inventoryMessage));
				} catch (JsonProcessingException e) {
					logger.error("Error while converting object to json: %s".formatted(e.getMessage()));
				}
			}
		});
	}

	@Transactional
	@KafkaListener(topics = "order-inventory-response")
	public void listenInventoryResponseMessage(String inventoryResponseMesssage) throws Exception {
		var responseMessage = objectMapper.readValue(inventoryResponseMesssage, InventoryResponseMessage.class);
		orderRepository.findById(responseMessage.orderId()).ifPresent(order -> {
			if (order.getStatus() == OrderStatus.PAYMENT) {
				if (responseMessage.inventoryStatus() == InventoryStatus.IN_STOCK)
					order.setStatus(OrderStatus.SENT);
				else if (responseMessage.inventoryStatus() == InventoryStatus.NOT_IN_STOCK) {
					order.setStatus(OrderStatus.NOT_IN_STOCK);
				}
				orderRepository.save(order);
			}
		});
	}

	@Transactional
	@KafkaListener(topics = "order-inventory-instock-response")
	public void listenInventoryInStockResponseMessage(String inventoryResponseMesssage) throws Exception {
		var responseMessage = objectMapper.readValue(inventoryResponseMesssage, InventoryResponseMessage.class);

		if (responseMessage.inventoryStatus() == InventoryStatus.NOT_IN_STOCK) {
			var order = orderRepository.findById(responseMessage.orderId()).orElseThrow();
			switch (order.getStatus()) {
			case PAYMENT -> {
				cancelPayment(order);
				cancelOrder(order.getOrderId());
			}
			case CREATED -> {
			}
			case NOT_IN_STOCK -> {
				cancelPayment(order);
				cancelInventory(order.getOrderId());
				cancelOrder(order.getOrderId());
			}
			default -> throw new IllegalArgumentException("Unexpected value: " + order.getStatus());
			}
		}
	}

	@Compansation(action = OrderAction.VALIDATE_PAYMENT)
	@Transactional
	public void cancelPayment(Order order) {
		var cancelPayment = new CancelPayment(order.getCustomerId(), order.getOrderId(), order.getTotal());
		try {
			kafkaTemplate.send("order-payment", objectMapper.writeValueAsString(cancelPayment));
		} catch (JsonProcessingException e) {
			logger.error("Error while converting object to json: %s".formatted(e.getMessage()));
		}
	}

	@Compansation(action = OrderAction.DROP_FROM_INVENTORY)
	@Transactional
	public void cancelInventory(long orderId) {
		orderRepository.findById(orderId).ifPresent(order -> {
			List<InventoryItem> items = order.getItems().stream()
					.map(item -> new InventoryItem(item.getSku(), item.getQuantity())).toList();
			var cancelInventoryMessage = new CancelInventoryMessage(order.getOrderId(), items);
			try {
				kafkaTemplate.send("order-inventory", objectMapper.writeValueAsString(cancelInventoryMessage));
			} catch (JsonProcessingException e) {
				logger.error("Error while converting object to json: %s".formatted(e.getMessage()));
			}
		});
	}

	@Transactional
	@Compansation(action = OrderAction.CREATE_ORDER)
	public void cancelOrder(long orderId) {
		Consumer<Order> changeOrderStatusToCanceled = order -> order.setStatus(OrderStatus.CANCELED);
		Consumer<Order> saveOrder = order -> orderRepository.save(order);
		orderRepository.findById(orderId).ifPresent(changeOrderStatusToCanceled.andThen(saveOrder));
	}
}
