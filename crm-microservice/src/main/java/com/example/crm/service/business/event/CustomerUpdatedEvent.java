package com.example.crm.service.business.event;

public class CustomerUpdatedEvent extends CrmBaseEvent{

	public CustomerUpdatedEvent(String identityNo) {
		super(identityNo, CrmEventType.CUSTOMER_UPDATED_EVENT);
	}

}
