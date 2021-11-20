package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import com.manutencao_predial.model.BuildingMaterial;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BuildingMaterialServiceTest {
    @Mock
    BuildingMaterialService mock;
    @Mock
    BuildingMaterialService mock2;

    @Test
    public void checkRemoveItemException() throws Exception {
        try {
            when(mock.removeBuildingMaterialService(2, 25)).thenThrow(new Exception("Material nao encontrado"));
            mock.removeBuildingMaterialService(2, 25);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Material nao encontrado", e.getMessage());
        }
        try {
            when(mock.removeBuildingMaterialService(1, -1)).thenThrow(new Exception("Valor abaixo do minimo"));
            mock.removeBuildingMaterialService(1, -1);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Valor abaixo do minimo", e.getMessage());
        }
        try {
            when(mock.removeBuildingMaterialService(1, 51)).thenThrow(new Exception("Nao existe material suficiente no estoque"));
            mock.removeBuildingMaterialService(1, 51);
            fail("Nao deu a excecao esperada");
        }catch(Exception e){
            assertEquals("Nao existe material suficiente no estoque", e.getMessage());
        }
        BuildingMaterial buildingMaterial = new BuildingMaterial("Lampada", new BigDecimal(25), 50);
        try {
            when(mock.removeBuildingMaterialService(1, 0)).thenReturn(buildingMaterial);
            buildingMaterial = mock.removeBuildingMaterialService(1, 0);
            assertEquals(50, buildingMaterial.getAmount());
        } catch (Exception e) {
            fail("Ta errado!");
        }
        try {
            buildingMaterial.setAmount(0);
            when(mock.removeBuildingMaterialService(1, 50)).thenReturn(buildingMaterial);
            buildingMaterial = mock.removeBuildingMaterialService(1, 0);
            assertEquals(0, buildingMaterial.getAmount());
        } catch (Exception e) {
            fail("Ta errado!");
        }

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(mock, times(5)).removeBuildingMaterialService(argument.capture(), argument.capture());

    }

    @Test
    public void addItem() {
        BuildingMaterial buildingMaterial = new BuildingMaterial("Ceramica", new BigDecimal(25), 51);
        
        when(mock2.checkBuildingMaterialIsValid(buildingMaterial)).thenReturn(false);
        assertFalse(mock2.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(50);
        when(mock.checkBuildingMaterialIsValid(buildingMaterial)).thenReturn(true);
        assertEquals(true, mock.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(49);
        assertEquals(true, mock.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(1);
        assertEquals(true, mock.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(0);
        assertEquals(true, mock.checkBuildingMaterialIsValid(buildingMaterial));

        buildingMaterial.setAmount(-1);
        assertFalse(mock2.checkBuildingMaterialIsValid(buildingMaterial));
        
        verify(mock, times(4)).checkBuildingMaterialIsValid(buildingMaterial);
        verify(mock2, times(2)).checkBuildingMaterialIsValid(buildingMaterial);
    }
}
