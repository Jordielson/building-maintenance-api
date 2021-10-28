package com.manutencao_predial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manutencao_predial.model.BuildingMaterial;

public interface BuildingMaterialRepository extends JpaRepository<BuildingMaterial, Integer>{

    @Query("SELECT m FROM BuildingMaterial m")
	Page<BuildingMaterial> findBuildingMaterialsOrderByAmountDesc(Pageable pageable);
}
