package com.manutencao_predial.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="immobile")
public class Immobile extends RepresentationModel<Immobile>implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String address;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company")
	private Company company;

	@Enumerated(EnumType.STRING)
	private ImmobileSizeEnum immobileSize;

	@ManyToMany
	private List<User> employees;

	public Immobile() {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	public ImmobileSizeEnum getImmobileSize() {
		return immobileSize;
	}
	public void setImmobileSize(ImmobileSizeEnum immobileSize) {
		this.immobileSize = immobileSize;
	}
	public List<User> getEmployees() {
		return employees;
	}
	public void setEmployees(List<User> employees) {
		this.employees = employees;
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
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Immobile) {
			Immobile i = (Immobile) obj;
			return this.id == i.id;
		}
		return false;
	}
	
}
