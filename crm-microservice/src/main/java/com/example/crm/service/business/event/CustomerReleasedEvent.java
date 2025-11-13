package com.example.crm.service.business.event;

public class CustomerReleasedEvent extends CrmBaseEvent {

	public CustomerReleasedEvent(String identityNo) {
		super(identityNo, CrmEventType.CUSTOMER_RELEASED_EVENT);
	}

}
