package com.example.integration.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.integration.dto.response.Ticker;

@Service
public class BinanceRestApiConsumer {

	private final RestTemplate restTemplate;
	
	
	public BinanceRestApiConsumer(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}


	@Scheduled(fixedRate = 5_000)
	public void sendTickerRequest() {
		var response = restTemplate.getForEntity("https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT", Ticker.class).getBody().toString();
		System.out.println(response);
	}
}
