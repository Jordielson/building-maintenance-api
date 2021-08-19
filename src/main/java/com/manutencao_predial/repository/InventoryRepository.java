package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manutencao_predial.model.BuildingMaterial;
import com.manutencao_predial.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, BuildingMaterial>{

}
