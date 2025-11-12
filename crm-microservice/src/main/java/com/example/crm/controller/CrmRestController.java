package com.example.crm.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/customers")
@Validated
@CrossOrigin
@Tag(name = "Customers", description = "Operations related to customers")
public class CrmRestController {

	private final CustomerService customerService;

	public CrmRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	// Query
	@Operation(summary = "Get customer by Identity No", description = "Returns one customer including its addresses and phones.")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderDto.class))),
			@ApiResponse(responseCode = "404", description = "Not found") })
	@GetMapping("{identityNo}")
	public CustomerDocument getCustomerById(
			@Parameter(description = "Identity No", example = "11111111110")
			@PathVariable 
			@TcKimlikNo String identityNo) {
		return customerService.findById(identityNo);
	}

	@GetMapping(params = { "page", "size" })
	public List<CustomerDocument> getCustomers(@Min(0) @RequestParam int page, @Max(50) @RequestParam int size) {
		return customerService.findAll(PageRequest.of(page, size));
	}

	// Command
	@PostMapping
	public AcquireCustomerResponse acquire(@RequestBody @Validated CustomerDocument customer) {
		return customerService.acquire(customer);
	}

	@PutMapping("{identityNo}")
	public UpdateCustomerResponse update(@PathVariable @TcKimlikNo String identityNo,
			@Validated @RequestBody CustomerDocument customer) {
		return customerService.update(identityNo, customer);
	}

	@PatchMapping("{identityNo}")
	public UpdateCustomerResponse patch(@PathVariable @TcKimlikNo String identityNo, Map<String, Object> values) {
		return customerService.patch(identityNo, values);
	}

	@DeleteMapping("{identityNo}")
	public CustomerDocument releaseCustomerById(@PathVariable @TcKimlikNo String identityNo) {
		return customerService.release(identityNo);
	}

}
