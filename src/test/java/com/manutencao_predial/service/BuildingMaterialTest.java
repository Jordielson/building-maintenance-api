package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;

import com.manutencao_predial.model.BuildingMaterial;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuildingMaterialTest {
    @Autowired
    BuildingMaterialService buildingMaterialService;

    @Test
    public void checkRemoveItemException() {
        try {
            buildingMaterialService.removeBuildingMaterialService(2, 25);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Material nao encontrado", e.getMessage());
        }
        try {
            buildingMaterialService.removeBuildingMaterialService(1, -1);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Valor abaixo do minimo", e.getMessage());
        }
        try {
            buildingMaterialService.removeBuildingMaterialService(1, 51);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Nao existe material suficiente no estoque", e.getMessage());
        }
        BuildingMaterial buildingMaterial;
        try {
            buildingMaterial = buildingMaterialService.removeBuildingMaterialService(1, 0);
            assertEquals(50, buildingMaterial.getAmount());
        } catch (Exception e) {
            fail("Ta errado!");
        }
        try {
            buildingMaterial = buildingMaterialService.removeBuildingMaterialService(1, 50);
            assertEquals(0, buildingMaterial.getAmount());
        } catch (Exception e) {
            fail("Ta errado!");
        }
    }

    @Test
    public void addItem() {
        BuildingMaterial buildingMaterial = new BuildingMaterial("Ceramica", new BigDecimal(25), 51);
        assertFalse(buildingMaterialService.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(50);
        assertEquals(true, buildingMaterialService.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(49);
        assertEquals(true, buildingMaterialService.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(1);
        assertEquals(true, buildingMaterialService.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(0);
        assertEquals(true, buildingMaterialService.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(-1);
        try {
            buildingMaterialService.checkBuildingMaterialIsValid(buildingMaterial);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Quantidade menor que o valor minino", e.getMessage());
        }
    }
}
