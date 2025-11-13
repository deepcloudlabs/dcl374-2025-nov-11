package com.example.crm.service.business.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class CrmBaseEvent {
	private final String eventId = UUID.randomUUID().toString();
	private final String identityNo;
	private final CrmEventType eventType;
	private final ZonedDateTime createdAt = ZonedDateTime.now();

	public CrmBaseEvent(String identityNo, CrmEventType eventType) {
		this.identityNo = identityNo;
		this.eventType = eventType;
	}

	public String getEventId() {
		return eventId;
	}

	public String getIdentityNo() {
		return identityNo;
	}

	public CrmEventType getEventType() {
		return eventType;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}

}
