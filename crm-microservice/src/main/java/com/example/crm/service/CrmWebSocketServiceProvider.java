package com.example.crm.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.crm.service.business.event.CrmBaseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CrmWebSocketServiceProvider implements WebSocketHandler, HandleCrmEvent {

	private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;

	public CrmWebSocketServiceProvider(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.put(session.getId(), session);
		System.err.println("New websocket client has arrived: %s".formatted(session.getId()));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// expected no message from the client!
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		System.out.println("Error has occured on session [%s]: %s".formatted(session.getId(), exception.getMessage()));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions.remove(session.getId());
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	@Override
	@EventListener
	@Async(value = "events-executor")
	public void listenCrmEvents(CrmBaseEvent event) {
		try {
			var eventAsJson = objectMapper.writeValueAsString(event);
			var message = new TextMessage(eventAsJson);
			sessions.forEach((sessionId, session) -> {
				try {
					session.sendMessage(message);
				} catch (IOException e) {
					System.err.println("Error has occured while sending message to the websocket client [%s]: %s"
							.formatted(sessionId, e.getMessage()));
				}
			});
		} catch (JsonProcessingException e) {
			System.err.println("Error has occured while convert to JSON: %s".formatted(e.getMessage()));
		}
	}
}
