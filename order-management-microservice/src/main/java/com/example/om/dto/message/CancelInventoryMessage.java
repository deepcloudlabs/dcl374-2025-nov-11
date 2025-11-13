package com.example.om.dto.message;

import java.util.List;

public record CancelInventoryMessage(long orderId, List<InventoryItem> items) {
}
