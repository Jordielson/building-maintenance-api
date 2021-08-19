package com.manutencao_predial.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="inventory")
public class Inventory extends RepresentationModel<Inventory> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material_id")
	private BuildingMaterial buildingMaterial;
	
	@Column 
	private int amount;
	
	public Inventory() {
	}

	public BuildingMaterial getBuildingMaterial() {
		return buildingMaterial;
	}

	public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
		this.buildingMaterial = buildingMaterial;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return buildingMaterial.getName();
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = buildingMaterial.hashCode();
		result = prime * amount;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Inventory) {
			Inventory i = (Inventory) obj;
			return this.buildingMaterial.equals(i.buildingMaterial);
		}
		return false;
	}
}
