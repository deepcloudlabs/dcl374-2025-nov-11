package com.example.crm.service.business.event;

public class CustomerAcquiredEvent extends CrmBaseEvent{

	public CustomerAcquiredEvent(String identityNo) {
		super(identityNo, CrmEventType.CUSTOMER_ACQUIRED_EVENT);
	}
	
}
