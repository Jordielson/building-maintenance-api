package com.manutencao_predial.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="service")
public class Service extends RepresentationModel<Service> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String title;

	@Column
	private float budget;
	
	@Column
	private String description;
	
	@Column
	private String term;
	
	@Column
	private String state;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room")
	private Room room;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manager")
	private User manager;
	
	@ManyToMany(cascade = CascadeType.REFRESH)
	@JoinTable(name = "providers_service",
				joinColumns = @JoinColumn(name = "service"),
				inverseJoinColumns = @JoinColumn(name = "providers"))
	private List<User> providers;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public List<User> getProviders() {
		return providers;
	}

	public void setProviders(List<User> providers) {
		this.providers = providers;
	}
	public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = id;
		result = prime * result + (int) budget;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((manager == null) ? 0 : manager.hashCode());
		result = prime * result + ((providers == null) ? 0 : providers.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Service) {
			Service s = (Service) obj;
			return this.id == s.id;
		}
		return false;
	}
	
}
