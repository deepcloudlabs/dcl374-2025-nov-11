package com.example.crm.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.crm.document.CustomerDTO;

@FeignClient(name = "crm")
public interface CrmService {
	@GetMapping("/api/v1/customers/{identityNo}")
	CustomerDTO getCustomerById(@PathVariable String identityNo);
}
