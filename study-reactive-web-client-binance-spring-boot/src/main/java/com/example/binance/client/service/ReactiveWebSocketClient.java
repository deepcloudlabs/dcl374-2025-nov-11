package com.example.binance.client.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;

import jakarta.annotation.PostConstruct;

@Service
public class ReactiveWebSocketClient {
	private final ReactorNettyWebSocketClient client;
    private final String binanceWsUrl;

    public ReactiveWebSocketClient(@Value("${binanceWsUrl}") String binanceWsUrl) {
        this.client = new ReactorNettyWebSocketClient();
        this.binanceWsUrl = binanceWsUrl;
    }
    
    @PostConstruct
    public void connect() {
        URI uri = URI.create(binanceWsUrl);

        client.execute(uri, this::handleSession)
              .subscribe();  
    }

    private reactor.core.publisher.Mono<Void> handleSession(WebSocketSession session) {
        return session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(System.out::println)
                .then();  
    }
}
