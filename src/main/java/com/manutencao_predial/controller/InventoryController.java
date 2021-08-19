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

import com.manutencao_predial.model.Inventory;
import com.manutencao_predial.repository.InventoryRepository;

@RestController
@RequestMapping(value="/building-maintenance")
public class InventoryController {

	@Autowired
	InventoryRepository inventoryRepository;
	
	@GetMapping("/inventory")
	public ResponseEntity<List<Inventory>> getAll() {
		List<Inventory> inventory = inventoryRepository.findAll();
		for (Inventory item : inventory) {
			int id = item.getId();
			item.add(linkTo(methodOn(InventoryController.class).getItem(id)).withSelfRel());
		}
		return new ResponseEntity<List<Inventory>>(inventory, HttpStatus.OK);
	}
	
	@GetMapping("/inventory/{id}")
	public ResponseEntity<Inventory> getItem(@PathVariable(value="id") int id) {
		Optional<Inventory> itemO = inventoryRepository.findById(id);
		if(!itemO.isPresent()) {
			return new ResponseEntity<Inventory>(HttpStatus.NOT_FOUND);
		} else {
			itemO.get().add(linkTo(methodOn(InventoryController.class).getAll()).withRel("Inventory list"));
			return new ResponseEntity<Inventory>(itemO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/inventory")
	public ResponseEntity<Inventory> saveItem(@RequestBody Inventory item) {
		return new ResponseEntity<Inventory>(inventoryRepository.save(item), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/inventory/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(value="id") int id) {
		Optional<Inventory> itemO = inventoryRepository.findById(id);
		if(!itemO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			inventoryRepository.delete(itemO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/inventory")
	public ResponseEntity<Inventory> updateItem(@RequestBody Inventory item) {
		return new ResponseEntity<Inventory>(inventoryRepository.save(item), HttpStatus.OK);
	}
}
