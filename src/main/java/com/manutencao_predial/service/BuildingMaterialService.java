package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.BuildingMaterial;
import com.manutencao_predial.repository.BuildingMaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingMaterialService {
    @Autowired
    BuildingMaterialRepository buildingMaterialRepository;

    public List<BuildingMaterial> findAll() {
        return buildingMaterialRepository.findAll();
    }

    public Optional<BuildingMaterial> findById(int id) {
        Optional<BuildingMaterial> buildingMaterialO = buildingMaterialRepository.findById(id);
		return buildingMaterialO;
    }

    public BuildingMaterial save(BuildingMaterial buildingMaterial) {
        return buildingMaterialRepository.save(buildingMaterial);
    }

    public BuildingMaterial update(BuildingMaterial buildingMaterial) {
        return buildingMaterialRepository.save(buildingMaterial);
    }

    public void delete(BuildingMaterial buildingMaterial) {
        buildingMaterialRepository.delete(buildingMaterial);
    }
}
