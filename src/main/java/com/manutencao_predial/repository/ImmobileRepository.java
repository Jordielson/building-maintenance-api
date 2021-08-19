package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manutencao_predial.model.Immobile;

public interface ImmobileRepository extends JpaRepository<Immobile, Integer>{
	
}
