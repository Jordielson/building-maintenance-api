package com.manutencao_predial.repository;

import com.manutencao_predial.model.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, String>{
    
}
