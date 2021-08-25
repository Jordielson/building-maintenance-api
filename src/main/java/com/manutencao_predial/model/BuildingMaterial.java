package com.manutencao_predial.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="building_material")
public class BuildingMaterial extends RepresentationModel<BuildingMaterial> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column 
	private float price;

	@Column
	private int amount = 0;
	
	public BuildingMaterial() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) price;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof BuildingMaterial) {
			BuildingMaterial m = (BuildingMaterial) obj;
			return this.id == m.id;
		}
		return false;
	}
}
