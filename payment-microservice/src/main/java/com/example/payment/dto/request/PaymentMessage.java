package com.example.payment.dto.request;

public record PaymentMessage(String customerId, long orderId, double total) {
}
