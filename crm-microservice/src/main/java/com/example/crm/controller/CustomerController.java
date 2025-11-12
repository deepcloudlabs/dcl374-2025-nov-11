package com.example.crm.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.crm.dto.response.CustomerQLResponse;
import com.example.crm.service.CustomerService;

@Controller
public class CustomerController {
	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@QueryMapping
	public CustomerQLResponse customerById(@Argument String identity) {
		System.out.println();
		return customerService.getCustomerQLResponse(identity);
	}
}
