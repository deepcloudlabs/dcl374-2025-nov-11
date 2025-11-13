package com.example.ob.application.event;

import com.example.ob.entity.OrderEntity;

public record OrderAcceptedEvent(String eventId,String eventType,OrderEntity order) {

}
