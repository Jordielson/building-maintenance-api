package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manutencao_predial.model.BuildingMaterial;

public interface BuildingMaterialRepository extends JpaRepository<BuildingMaterial, Integer>{

}
