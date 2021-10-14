package com.manutencao_predial.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="material_service")
public class MaterialService extends RepresentationModel<Service> implements Serializable {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
    @ManyToOne
    private BuildingMaterial buildingMaterial;

    @ManyToOne
    private Service service;

    private int amount;

    private BigDecimal priceUnit;

    public MaterialService() {
        
    }


    public int getAmount() {
        return amount;
    }
    public BuildingMaterial getBuildingMaterial() {
        return buildingMaterial;
    }
    public Service getService() {
        return service;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
        this.buildingMaterial = buildingMaterial;
    }
    public void setService(Service service) {
        this.service = service;
    }
    public BigDecimal getPriceUnit() {
        return priceUnit;
    }
    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

    public BigDecimal getFullValue() {
        return priceUnit.multiply(BigDecimal.valueOf(amount));
    }
}
