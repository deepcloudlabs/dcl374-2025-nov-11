package com.example.om.dto.message;

public record Payment(String customerId, long orderId, double total) {
}
