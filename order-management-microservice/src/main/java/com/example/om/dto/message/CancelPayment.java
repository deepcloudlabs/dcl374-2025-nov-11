package com.example.om.dto.message;

public record CancelPayment(String customerId, long orderId,double total) {
}
