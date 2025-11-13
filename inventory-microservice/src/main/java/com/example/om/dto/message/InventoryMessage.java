package com.example.om.dto.message;

import java.util.List;

public record InventoryMessage(long orderId, List<InventoryItem> items) {
}
