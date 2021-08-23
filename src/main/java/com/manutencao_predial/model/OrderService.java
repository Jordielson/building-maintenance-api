package com.manutencao_predial.model;

import java.io.Serializable;

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
@Table(name = "order_service")
public class OrderService extends RepresentationModel<OrderService> implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @Column
	private String title;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "room_material")
    private RoomMaterial roomMaterial;

    @Column
    private String description;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public RoomMaterial getRoomMaterial() {
        return roomMaterial;
    }
    public void setRoomMaterial(RoomMaterial roomMaterial) {
        this.roomMaterial = roomMaterial;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
	public String toString() {
		return description;
	}
	
	@Override
	public int hashCode() {
		final int prime = 7;
		int result = id;
		result = prime * result + ((roomMaterial == null) ? 0 : roomMaterial.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof OrderService) {
			OrderService o = (OrderService) obj;
			return this.id == o.id;
		}
		return false;
	}
}
