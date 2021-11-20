package com.manutencao_predial.repository;

import com.manutencao_predial.model.MaterialService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialServiceRepository extends JpaRepository<MaterialService, Integer>{

}
