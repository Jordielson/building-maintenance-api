package com.manutencao_predial.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manutencao_predial.model.Immobile;
import com.manutencao_predial.model.Room;
import com.manutencao_predial.service.RoomService;

@CrossOrigin(origins = "http://localhost:19006")
@RestController
@RequestMapping(value="/ManuPredSoft")
public class RoomResource {
	
	@Autowired
	RoomService roomService;
	
	@GetMapping("/room")
	public ResponseEntity<List<Room>> getAll() {
		List<Room> roomList = roomService.findAll();
		for (Room room : roomList) {
			int id = room.getId();
			Immobile immobile = room.getImmobile();
			immobile.add(linkTo(methodOn(ImmobileResource.class).getImmobile(immobile.getId())).withSelfRel());
			room.add(linkTo(methodOn(RoomResource.class).getRoom(id)).withSelfRel());
		}
		return new ResponseEntity<List<Room>>(roomList, HttpStatus.OK);
	}
	
	@GetMapping("/room/{id}")
	public ResponseEntity<Room> getRoom(@PathVariable(value="id") int id) {
		Optional<Room> roomO = roomService.findById(id);
		if(!roomO.isPresent()) {
			return new ResponseEntity<Room>(HttpStatus.NOT_FOUND);
		} else {
			roomO.get().add(linkTo(methodOn(RoomResource.class).getAll()).withRel("Room list"));
			return new ResponseEntity<Room>(roomO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/room")
	public ResponseEntity<Room> saveRoom(@RequestBody Room room) {
		return new ResponseEntity<Room>(roomService.save(room), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/room/{id}")
	public ResponseEntity<?> deleteRoom(@PathVariable(value="id") int id) {
		Optional<Room> roomO = roomService.findById(id);
		if(!roomO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			roomService.delete(roomO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/room")
	public ResponseEntity<Room> updateRoom(@RequestBody Room room) {
		return new ResponseEntity<Room>(roomService.save(room), HttpStatus.OK);
	}
}
