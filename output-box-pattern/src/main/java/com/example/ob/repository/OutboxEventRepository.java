package com.example.ob.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ob.entity.OutboxEvent;
import com.example.ob.entity.OutboxEventStatus;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, String> {

	List<OutboxEvent> findByStatusOrderByCreatedAtDesc(PageRequest page,OutboxEventStatus status);

}
