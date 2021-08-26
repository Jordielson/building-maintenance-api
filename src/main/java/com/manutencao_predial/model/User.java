package com.manutencao_predial.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="user")
public class User extends RepresentationModel<User> implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	private String cpf;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String fone;
	@Column
	private String job;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinTable(name = "user_immobile",
				joinColumns = @JoinColumn(name = "user_cpf"),
				inverseJoinColumns = @JoinColumn(name = "immobile_id"))
	private List<Immobile> workBuildings;
	
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
	public String getFone() {
		return fone;
	}
	public void setFone(String fone) {
		this.fone = fone;
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
}
