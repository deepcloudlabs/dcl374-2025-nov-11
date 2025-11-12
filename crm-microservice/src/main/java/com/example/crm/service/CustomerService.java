package com.example.crm.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.crm.document.CustomerDocument;
import com.example.crm.dto.response.AcquireCustomerResponse;
import com.example.crm.dto.response.UpdateCustomerResponse;
import com.example.crm.repository.CustomerDocumentRepository;

@Service
public class CustomerService {
	private final CustomerDocumentRepository customerDocumentRepository;

	public CustomerService(CustomerDocumentRepository customerDocumentRepository) {
		this.customerDocumentRepository = customerDocumentRepository;
	}

	public CustomerDocument findById(String identityNo) {
		return customerDocumentRepository.findById(identityNo).orElseThrow(() -> new IllegalArgumentException(
				"No such customer with the identity no(%s) exists.".formatted(identityNo)));
	}

	public List<CustomerDocument> findAll(PageRequest page) {
		return customerDocumentRepository.findAll(page).toList();
	}

	public AcquireCustomerResponse acquire(CustomerDocument customer) {
		customerDocumentRepository.insert(customer);
		return new AcquireCustomerResponse("success");
	}

	public UpdateCustomerResponse update(String identityNo, CustomerDocument customer) {
		customerDocumentRepository.save(customer);
		return new UpdateCustomerResponse("success");
	}

	public UpdateCustomerResponse patch(String identityNo, Map<String, Object> values) {
	    var customerDocument = customerDocumentRepository.findById(identityNo).orElseThrow(() -> new IllegalArgumentException());
	    for (var field: CustomerDocument.class.getDeclaredFields()) {
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
		return new UpdateCustomerResponse("success");
	}

	public CustomerDocument release(String identityNo) {
		var customerDocument = customerDocumentRepository.findById(identityNo).orElseThrow(() -> new IllegalArgumentException(
				"No such customer with the identity no(%s) exists.".formatted(identityNo)));
		customerDocumentRepository.delete(customerDocument);	
		return customerDocument;
	}

}
