package com.example.crm.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;

@Service
public class ClientSideLoadBalancingCrmConsumerService {
	private final DiscoveryClient discoveryClient;
	private final RestTemplate restTemplate;
	private List<ServiceInstance> instances;
	private final AtomicInteger counter = new AtomicInteger();
	
	
	public ClientSideLoadBalancingCrmConsumerService(DiscoveryClient discoveryClient, RestTemplate restTemplate) {
		this.discoveryClient = discoveryClient;
		this.restTemplate = restTemplate;
	}


	@PostConstruct
	public void loadServerInstancesList() {
		instances = discoveryClient.getInstances("crm");
		instances.forEach(instance -> System.out.println("%s:%d".formatted(instance.getHost(),instance.getPort())));
	}
	
	
	@Scheduled(fixedRate = 2_000)
	public void sendRequest() {
		var instance = instances.get(counter.getAndIncrement()%instances.size());
		var response = restTemplate.getForEntity("http://%s:%d/api/v1/customers/11111111110".formatted(instance.getHost(),instance.getPort()),String.class).getBody();
		System.out.println("[Client-side Load Balancing] RestTemplate has received the response: %s".formatted(response));
	}
}
