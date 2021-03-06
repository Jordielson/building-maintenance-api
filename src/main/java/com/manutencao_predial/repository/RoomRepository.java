package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manutencao_predial.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>{

}
