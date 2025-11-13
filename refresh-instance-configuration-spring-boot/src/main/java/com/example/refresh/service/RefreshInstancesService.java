package com.example.refresh.service;

import java.util.List;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.refresh.dto.request.RefreshInstancesRequest;
import com.example.refresh.dto.response.RefreshInstancesResponse;

@Service
public class RefreshInstancesService {
	private final DiscoveryClient discoveryClient;
	private final RestTemplate restTemplate;
	
	public RefreshInstancesService(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
	}

	public RefreshInstancesResponse refresh(RefreshInstancesRequest request) {
		var instances = discoveryClient.getInstances(request.serviceId());
		var numberOfInstances = instances.stream()
		         .mapToInt( instance -> {
		        	     var refreshUrl = "http://%s:%d/%s/actuator/refresh".formatted(instance.getHost(),instance.getPort(),request.baseUrl());
					     var headers = new HttpHeaders();
					     headers.setContentType(MediaType.APPLICATION_JSON);
					     headers.setAccept(List.of(MediaType.APPLICATION_JSON));
					     var httpEntity = new HttpEntity<>(new RefreshRequest(),headers);
					     try {
					    	 System.out.println(restTemplate.postForEntity(refreshUrl, httpEntity,String.class).getBody());
					     }catch (Exception e) {
							return 0;
						}
		        	     return 1;
		         }).sum();
		return new RefreshInstancesResponse(numberOfInstances, "Ok");
	}

}

record RefreshRequest() {}
