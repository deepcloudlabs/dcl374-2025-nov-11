package com.example.crm.service;

import com.example.crm.service.business.event.CrmBaseEvent;

public interface HandleCrmEvent {
	void listenCrmEvents(CrmBaseEvent event);
}
