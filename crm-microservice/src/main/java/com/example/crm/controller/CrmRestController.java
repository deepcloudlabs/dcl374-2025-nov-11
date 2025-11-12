package com.example.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.crm.document.CustomerDocument;
import com.example.crm.dto.response.AcquireCustomerResponse;
import com.example.crm.dto.response.UpdateCustomerResponse;
import com.example.crm.service.CustomerService;
import com.example.validation.TcKimlikNo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/customers")
@Validated
public class CrmRestController {

	private final CustomerService customerService;

	public CrmRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	// Query
	@GetMapping("{identityNo}")
	public CustomerDocument getCustomerById(
			@PathVariable @TcKimlikNo String identityNo) {
		return customerService.findById(identityNo);
	}
	
	@GetMapping(params={"page","size"})
	public List<CustomerDocument> getCustomers(
			@Min(0) @RequestParam int page,
			@Max(50) @RequestParam int size) {
		return customerService.findAll(PageRequest.of(page, size));
	}
	// Command
	@PostMapping
	public AcquireCustomerResponse acquire(
			@RequestBody @Validated CustomerDocument customer) {
		return customerService.acquire(customer);
	}
	
	@PutMapping("{identityNo}")
	public UpdateCustomerResponse update(
			@PathVariable @TcKimlikNo String identityNo,
			@Validated @RequestBody CustomerDocument customer) {
		return customerService.update(identityNo,customer);
	}	
	
	@PatchMapping("{identityNo}")
	public UpdateCustomerResponse patch(@PathVariable @TcKimlikNo String identityNo,Map<String,Object> values) {
		return customerService.patch(identityNo,values);
	}	
	
	@DeleteMapping("{identityNo}")
	public CustomerDocument releaseCustomerById(@PathVariable @TcKimlikNo String identityNo) {
		return customerService.release(identityNo);
	}
	
	
}
