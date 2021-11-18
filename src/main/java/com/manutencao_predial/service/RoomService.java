package com.manutencao_predial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Room;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.StateService;
import com.manutencao_predial.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
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

    public List<Room> findRoomsInMaintenance(List<Service> services) {
        List<Room> rooms = new ArrayList<>();
        for (Service service : services) {
            if (service.getState() == StateService.EXECUTANDO) {
                rooms.add(service.getRoom());
            }
        }
        return rooms;
    }
}
