package com.example.integration.service;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;

import jakarta.annotation.PostConstruct;

@Service
public class BinanceWebSocketClient implements WebSocketHandler {
	private final WebSocketClient webSocketClient;
	
	
	public BinanceWebSocketClient(WebSocketClient webSocketClient) {
		this.webSocketClient = webSocketClient;
	}


	@PostConstruct
	public void connect() {
		webSocketClient.execute(this, "wss://stream.binance.com:9443/ws/btcusdt@trade");
	}


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Connected to the binance websocket server.");
		
	}


	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println(message.getPayload().toString());
		
	}


	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
