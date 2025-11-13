package com.example.ob.entity;

import java.time.Instant;
import java.util.Objects;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "outbox_events")
@DynamicUpdate
public class OutboxEvent {
	@Id
	private String id;
	private String topic;
	private String eventType;
	private String payload;
	private Instant createdAt;
	private Instant publishedAt;
	private OutboxEventStatus status;

	public OutboxEvent() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Instant publishedAt) {
		this.publishedAt = publishedAt;
	}

	public OutboxEventStatus getStatus() {
		return status;
	}

	public void setStatus(OutboxEventStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OutboxEvent other = (OutboxEvent) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OutboxEvent [id=" + id + ", topic=" + topic + ", eventType=" + eventType + ", payload=" + payload
				+ ", createdAt=" + createdAt + ", publishedAt=" + publishedAt + ", status=" + status + "]";
	}

}
