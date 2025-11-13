package com.example.refresh.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.refresh.dto.request.RefreshInstancesRequest;
import com.example.refresh.dto.response.RefreshInstancesResponse;
import com.example.refresh.service.RefreshInstancesService;

@RestController
@RequestScope
@RequestMapping("/instances")
public class RefreshRestController {

	private final RefreshInstancesService refreshInstancesService;

	
	public RefreshRestController(RefreshInstancesService refreshInstancesService) {
		this.refreshInstancesService = refreshInstancesService;
	}


	@PostMapping
	public RefreshInstancesResponse refreshInstances(@RequestBody RefreshInstancesRequest request) {
		return refreshInstancesService.refresh(request);
	}
}
