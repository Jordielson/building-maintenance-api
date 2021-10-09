package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Room;
import com.manutencao_predial.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public Optional<Room> findById(int id) {
        Optional<Room> roomO = roomRepository.findById(id);
		return roomO;
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Room update(Room room) {
        return roomRepository.save(room);
    }

    public void delete(Room room) {
        roomRepository.delete(room);
    }
}
