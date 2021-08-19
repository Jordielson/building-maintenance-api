package com.manutencao_predial.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manutencao_predial.model.RoomMaterial;
import com.manutencao_predial.repository.RoomMaterialRepository;

@RestController
@RequestMapping(value="/building-maintenance")
public class RoomMaterialController {

	@Autowired
	RoomMaterialRepository roomMaterialRepository;
	
	@GetMapping("/room-material")
	public ResponseEntity<List<RoomMaterial>> getAll() {
		List<RoomMaterial> roomMaterialList = roomMaterialRepository.findAll();
		for (RoomMaterial roomMaterial : roomMaterialList) {
			int id = roomMaterial.getId();
			roomMaterial.add(linkTo(methodOn(RoomMaterialController.class).getRoomMaterial(id)).withSelfRel());
		}
		return new ResponseEntity<List<RoomMaterial>>(roomMaterialList, HttpStatus.OK);
	}
	
	@GetMapping("/room-material/{id}")
	public ResponseEntity<RoomMaterial> getRoomMaterial(@PathVariable(value="id") int id) {
		Optional<RoomMaterial> roomMaterialO = roomMaterialRepository.findById(id);
		if(!roomMaterialO.isPresent()) {
			return new ResponseEntity<RoomMaterial>(HttpStatus.NOT_FOUND);
		} else {
			roomMaterialO.get().add(linkTo(methodOn(RoomMaterialController.class).getAll()).withRel("Room Material list"));
			return new ResponseEntity<RoomMaterial>(roomMaterialO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/room-material")
	public ResponseEntity<RoomMaterial> saveRoomMaterial(@RequestBody RoomMaterial roomMaterial) {
		return new ResponseEntity<RoomMaterial>(roomMaterialRepository.save(roomMaterial), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/room-material/{id}")
	public ResponseEntity<?> deleteRoomMaterial(@PathVariable(value="id") int id) {
		Optional<RoomMaterial> roomMaterialO = roomMaterialRepository.findById(id);
		if(!roomMaterialO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			roomMaterialRepository.delete(roomMaterialO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/room-material")
	public ResponseEntity<RoomMaterial> updateRoomMaterial(@RequestBody RoomMaterial roomMaterial) {
		return new ResponseEntity<RoomMaterial>(roomMaterialRepository.save(roomMaterial), HttpStatus.OK);
	}
}
