package com.example.crm.service;

import java.util.List;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.crm.dto.request.Address;
import com.example.crm.dto.request.AddressType;
import com.example.crm.dto.request.CustomerDTO;
import com.example.crm.dto.request.Phone;
import com.example.crm.dto.request.PhoneType;

@Service
public class AsyncCrmConsumerService {
	private final WebClient webClient;

	public AsyncCrmConsumerService(WebClient webClient) {
		this.webClient = webClient;
	}
	
	@Scheduled(fixedRate=3_000)
	public void callCrmRestApi() {
		var customerDto = new CustomerDTO();
		String uuid = UUID.randomUUID().toString();
		customerDto.setIdentity(uuid);
		customerDto.setFullName("kate austen");
		customerDto.setEmail("%s@example.com".formatted(uuid));
		customerDto.setBirthYear(1987);
		customerDto.setPhones(List.of(new Phone("+90","555","555","55",PhoneType.HOME)));
		customerDto.setAddresses(List.of(new Address("Turkey","ankara","golbasi","333",AddressType.HOME)));
		webClient.post()
		        .bodyValue(customerDto)
		        .retrieve()
		        .bodyToMono(String.class)
		        .doOnNext( response -> {
		        	System.out.println(response);
		        })
		       .doOnError(error -> System.err.println(error))
		       .subscribe();
	}
}
