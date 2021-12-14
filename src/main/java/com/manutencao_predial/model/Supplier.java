package com.manutencao_predial.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="supplier")
public class Supplier extends RepresentationModel<Supplier> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String cnpj;

    @Column
    private String name;

    @ManyToMany
	private List<BuildingMaterial> buildingMaterials;

    public Supplier(){}

    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = cnpj.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Supplier) {
			Supplier s = (Supplier) obj;
			return this.cnpj.equals(s.cnpj);
		}
		return false;
	}
}
