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

    /**
     * Remove um material de construcao do estoque
     * @param idBuildingMaterial id do material de construcao
     * @param amount quantidade a ser removida
     * @throws Exception caso o material nao exista no estoque ou 
     *          a quantidade a ser removida seja maior que a quantidade no estoque
     */
    public BuildingMaterial removeBuildingMaterialService(int idBuildingMaterial, int amount) throws Exception {
        Optional<BuildingMaterial> buildingMaterialO = findById(idBuildingMaterial);
        if(!buildingMaterialO.isPresent()){
            throw new Exception("Material nao encontrado");
        }
        BuildingMaterial buildingMaterial = buildingMaterialO.get();
        if(amount < 0) {
            throw new Exception("Valor abaixo do minimo");
        }
        else if(amount > buildingMaterial.getAmount()) {
            throw new Exception("Nao existe material suficiente no estoque");
        }
        buildingMaterial.setAmount(buildingMaterial.getAmount() - amount);
        return buildingMaterial;
    }

    public Optional<BuildingMaterial> findById(int id) {
        Optional<BuildingMaterial> buildingMaterialO = buildingMaterialRepository.findById(id);
		return buildingMaterialO;
    }

    public boolean checkBuildingMaterialIsValid(BuildingMaterial buildingMaterial) throws RuntimeException {
        if(buildingMaterial.getAmount() > 50) {
            throw new RuntimeException("Quantidade de excedeu o limite");
        } else if(buildingMaterial.getAmount() < 0) {
            throw new RuntimeException("Quantidade menor que o valor minino");
        }
        return true;
    }

    public BuildingMaterial save(BuildingMaterial buildingMaterial) {
        checkBuildingMaterialIsValid(buildingMaterial);
        return buildingMaterialRepository.save(buildingMaterial);
    }

    public BuildingMaterial update(BuildingMaterial buildingMaterial) {
        return buildingMaterialRepository.save(buildingMaterial);
    }

    public void delete(BuildingMaterial buildingMaterial) {
        buildingMaterialRepository.delete(buildingMaterial);
    }
}
