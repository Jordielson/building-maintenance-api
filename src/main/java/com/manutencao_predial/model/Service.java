package com.manutencao_predial.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	private BigDecimal budget;
	
	@Column
	private String description;

	@Column
	private LocalDate initDate;
	
	@Column
	private LocalDate term;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	private StateService state;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room")
	private Room room;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "manager")
	private User manager;

	@OneToMany
	@JoinTable(name = "material_service",
		joinColumns = @JoinColumn(
			name = "service_id"
		),
		inverseJoinColumns = @JoinColumn(
			name = "id"
		)
	)
	private List<MaterialService> materials;
	
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinTable(name = "providers_service",
				joinColumns = @JoinColumn(name = "service"),
				inverseJoinColumns = @JoinColumn(name = "providers"))
	private List<User> providers =  new ArrayList<User>();


	public Service() {
	}

	public Service(int id, String title, BigDecimal budget, String description, StateService state, Room room) {
		this.id = id;
		this.title = title;
		this.budget = budget;
		this.description = description;
		this.state = state;
		this.room = room;
	}

	public Service id(int id) {
		setId(id);
		return this;
	}

	public Service title(String title) {
		setTitle(title);
		return this;
	}

	public Service budget(BigDecimal budget) {
		setBudget(budget);
		return this;
	}

	public Service description(String description) {
		setDescription(description);
		return this;
	}

	public Service initDate(LocalDate initDate) {
		setInitDate(initDate);
		return this;
	}

	public Service term(LocalDate term) {
		setTerm(term);
		return this;
	}

	public Service state(StateService state) {
		setState(state);
		return this;
	}

	public Service room(Room room) {
		setRoom(room);
		return this;
	}

	public Service manager(User manager) {
		setManager(manager);
		return this;
	}

	public Service materials(List<MaterialService> materials) {
		setMaterials(materials);
		return this;
	}

	public Service providers(List<User> providers) {
		setProviders(providers);
		return this;
	}


	public void addProvider(User user) {
		providers.add(user);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getTerm() {
		return term;
	}

	public void setTerm(LocalDate term) {
		this.term = term;
	}

	public StateService getState() {
		return state;
	}

	public void setState(StateService state) {
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
	public List<MaterialService> getMaterials() {
		return materials;
	}
	public void setMaterials(List<MaterialService> materials) {
		this.materials = materials;
	}
	public void addMaterial(MaterialService materialService) {
		this.materials.add(materialService);
		this.budget.add(materialService.getFullValue());
	}
	public void updateBudget() {
		this.budget = new BigDecimal(0);
		for (MaterialService materialService : materials) {
			this.budget.add(materialService.getFullValue());
		}
	}
	public LocalDate getInitDate() {
		return initDate;
	}
	public void setInitDate(LocalDate initDate) {
		this.initDate = initDate;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = id;
		result = prime * result + ((budget==null) ? 0 : budget.intValue());
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
