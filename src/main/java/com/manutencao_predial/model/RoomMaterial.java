package com.manutencao_predial.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name="room_matrial")
public class RoomMaterial extends RepresentationModel<RoomMaterial> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="last_maintenance")
	private LocalDate lastMaintenance;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_id")
	private Room room;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material_id")
	private BuildingMaterial buildingMaterial;
	
	public RoomMaterial() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getLastMaintenance() {
		return lastMaintenance;
	}

	public void setLastMaintenance(LocalDate lastMaintenance) {
		this.lastMaintenance = lastMaintenance;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public BuildingMaterial getBuildingMaterial() {
		return buildingMaterial;
	}

	public void setBuildingMaterial(BuildingMaterial buildingMaterial) {
		this.buildingMaterial = buildingMaterial;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = id;
		result = prime * result + ((lastMaintenance == null) ? 0 : lastMaintenance.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((buildingMaterial == null) ? 0 : buildingMaterial.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof RoomMaterial) {
			RoomMaterial rm = (RoomMaterial) obj;
			return this.id == rm.id;
		}
		return false;
	}
	
}
