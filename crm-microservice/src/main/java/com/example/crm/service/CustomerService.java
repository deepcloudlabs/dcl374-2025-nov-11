package com.example.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.crm.document.CustomerDocument;
import com.example.crm.dto.response.AcquireCustomerResponse;
import com.example.crm.dto.response.Address;
import com.example.crm.dto.response.CustomerQLResponse;
import com.example.crm.dto.response.Phone;
import com.example.crm.dto.response.UpdateCustomerResponse;
import com.example.crm.repository.CustomerDocumentRepository;
import com.example.crm.service.business.event.CustomerAcquiredEvent;
import com.example.crm.service.business.event.CustomerReleasedEvent;
import com.example.crm.service.business.event.CustomerUpdatedEvent;

@Service
public class CustomerService {
	private final CustomerDocumentRepository customerDocumentRepository;
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public CustomerService(CustomerDocumentRepository customerDocumentRepository, ApplicationEventPublisher applicationEventPublisher) {
		this.customerDocumentRepository = customerDocumentRepository;
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public CustomerDocument findById(String identityNo) {
		return customerDocumentRepository.findById(identityNo).orElseThrow(() -> new IllegalArgumentException(
				"No such customer with the identity no [%s] exists.".formatted(identityNo)));
	}

	public List<CustomerDocument> findAll(PageRequest page) {
		return customerDocumentRepository.findAll(page).toList();
	}

	@Transactional
	public AcquireCustomerResponse acquire(CustomerDocument customer) {
		customerDocumentRepository.insert(customer);
		var event = new CustomerAcquiredEvent(customer.getIdentityNo());
		applicationEventPublisher.publishEvent(event);
		return new AcquireCustomerResponse("success");
	}

	@Transactional
	public UpdateCustomerResponse update(String identityNo, CustomerDocument customer) {
		customerDocumentRepository.save(customer);
		var event = new CustomerUpdatedEvent(customer.getIdentityNo());
		applicationEventPublisher.publishEvent(event);
		return new UpdateCustomerResponse("success");
	}

	@Transactional
	public UpdateCustomerResponse patch(String identityNo, Map<String, Object> values) {
		var customerDocument = customerDocumentRepository.findById(identityNo)
				.orElseThrow(() -> new IllegalArgumentException(
						"No such customer with the identity no [%s] exists.".formatted(identityNo)));
		for (var field : CustomerDocument.class.getDeclaredFields()) {
			final var fieldName = field.getName();
			if (values.containsKey(fieldName)) {
				field.setAccessible(true);
				try {
					field.set(customerDocument, values.get(fieldName));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new IllegalArgumentException(e.getMessage());
				}
				field.setAccessible(false);
			}
		}
		customerDocumentRepository.save(customerDocument);
		var event = new CustomerUpdatedEvent(identityNo);
		applicationEventPublisher.publishEvent(event);
		return new UpdateCustomerResponse("success");
	}

	@Transactional
	public CustomerDocument release(String identityNo) {
		var customerDocument = customerDocumentRepository.findById(identityNo)
				.orElseThrow(() -> new IllegalArgumentException(
						"No such customer with the identity no [%s] exists.".formatted(identityNo)));
		customerDocumentRepository.delete(customerDocument);
		var event = new CustomerReleasedEvent(identityNo);
		applicationEventPublisher.publishEvent(event);
		return customerDocument;
	}

	public CustomerQLResponse getCustomerQLResponse(String identityNo) {
		var customerDocument = customerDocumentRepository.findById(identityNo).orElseThrow(() -> new IllegalArgumentException(
				"No such customer with the identity no [%s] exists.".formatted(identityNo)));
		
		var customerQLResponse = new CustomerQLResponse(
				identityNo, 
				customerDocument.getFullName(), 
				customerDocument.getEmail(), 
				customerDocument.getIban(), 
				customerDocument.getAddresses().stream().map(addr -> new Address(addr.getType(), addr.getCountry(), addr.getCity(), addr.getLine())).toList(), 
				customerDocument.getPhones().stream().map(phone -> new Phone(phone.getType(),phone.getCountryCode(),phone.getNumber(),phone.getExtension())).toList()
		);
		return customerQLResponse;
	}

}
