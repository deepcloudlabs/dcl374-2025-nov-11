package com.example.binance.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
public class WebClientConfig {

    @Bean
    WebClient webClient(WebClient.Builder builder,@Value("${binanceUrl}") String binanceUrl) {
        return builder
                .baseUrl(binanceUrl)
                .build();
    }
}
