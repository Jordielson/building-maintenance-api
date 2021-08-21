package com.manutencao_predial.repository;

import com.manutencao_predial.model.OrderService;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderServiceRepository extends JpaRepository<OrderService, Integer> {
    
}
