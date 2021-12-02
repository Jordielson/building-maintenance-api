package com.manutencao_predial.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="user")
public class User extends RepresentationModel<User> implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String cpf;
	@Column
	private String name;
	@Column(unique = true)
	private String email;
	@Column
	private String password;
	@Column
	private String fone;
	@Column
	private String job;
	@Column
	private LocalDate date;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "employees")
	private List<Immobile> workBuildings = new ArrayList<>();
	
	public User() {
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public List<Immobile> getWorkBuildings() {
		return workBuildings;
	}

	public void setWorkBuildings(List<Immobile> workBuildings) {
		this.workBuildings = workBuildings;
	}

	public void addWorkBuilding(Immobile immobile) {
		this.workBuildings.add(immobile);
	}

	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = cpf.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((job == null) ? 0 : job.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof User) {
			User u = (User) obj;
			return this.cpf.equals(u.cpf);
		}
		return false;
	}

	public int ageCalculate() {
		LocalDate localDate = LocalDate.now();
		int year = localDate.getYear();
		int month = localDate.getMonthValue();
		int day = localDate.getDayOfMonth();


		LocalDate userDate = getDate();
		int userYear = userDate.getYear();
		int userMonth = userDate.getMonthValue();
		int userDay = userDate.getDayOfMonth();
		
		if(userMonth <= month) {
			if(userMonth == month && userDay>day) {
				return (year-userYear)-1;
			}
			return year-userYear;
		}else {
			return (year-userYear)-1;
		}

	}
}