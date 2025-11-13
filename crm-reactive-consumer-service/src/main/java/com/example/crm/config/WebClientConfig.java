package com.example.crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class WebClientConfig {
	private final String crmRestApiUrl;
	
	public WebClientConfig(@Value("${crmRestApiUrl}") String hrRestApiUrl) {
		this.crmRestApiUrl = hrRestApiUrl;
	}
	
	@Bean
	WebClient createWebClient() {
		return WebClient.builder()
				        .baseUrl(crmRestApiUrl)
				        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				        .build();
	}
}
