package com.example.ob.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ob.entity.OrderEntity;

public interface OrderEntityRepository extends JpaRepository<OrderEntity, String>{

}
