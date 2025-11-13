package com.example.binance.client.service;

import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.binance.client.dto.response.TickerDTO;

@Service
public class BinanceReactiveWebClientService {
	private final WebClient webClient;
	
	public BinanceReactiveWebClientService(WebClient webClient) {
		this.webClient = webClient;
	}

	@Scheduled(fixedRate = 30_000)
	public void reactiveCall() {
		 webClient.get()
	              .uri("/price")
	              .accept(MediaType.APPLICATION_JSON)
	              .retrieve()
	              .bodyToFlux(TickerDTO.class)
	              .doOnNext(System.out::println)
	              .subscribe();
	}
}
