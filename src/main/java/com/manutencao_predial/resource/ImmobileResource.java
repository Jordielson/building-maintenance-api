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
import com.manutencao_predial.service.ImmobileService;

@CrossOrigin(origins = "http://localhost:19006")
@RestController
@RequestMapping(value="/ManuPredSoft")
public class ImmobileResource {
	
	@Autowired
	ImmobileService immobileService;
	
	@GetMapping("/immobile")
	public ResponseEntity<List<Immobile>> getAll() {
		List<Immobile> immobileList = immobileService.findAll();
		for (Immobile i : immobileList) {
			int id = i.getId();
			i.add(linkTo(methodOn(ImmobileResource.class).getImmobile(id)).withSelfRel());
		}
		return new ResponseEntity<List<Immobile>>(immobileList, HttpStatus.OK);
	}
	
	@GetMapping("/immobile/{id}")
	public ResponseEntity<Immobile> getImmobile(@PathVariable(value="id") int id) {
		Optional<Immobile> immO = immobileService.findById(id);
		if(!immO.isPresent()) {
			return new ResponseEntity<Immobile>(HttpStatus.NOT_FOUND);
		} else {
			immO.get().add(linkTo(methodOn(ImmobileResource.class).getAll()).withRel("Immobile list"));
			return new ResponseEntity<Immobile>(immO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/immobile")
	public ResponseEntity<Immobile> saveImmobile(@RequestBody Immobile immobile) {
		return new ResponseEntity<Immobile>(immobileService.save(immobile), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/immobile/{id}")
	public ResponseEntity<?> deleteImmobile(@PathVariable(value="id") int id) {
		Optional<Immobile> immO = immobileService.findById(id);
		if(!immO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			immobileService.delete(immO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/immobile")
	public ResponseEntity<Immobile> updateImmobile(@RequestBody Immobile immobile) {
		return new ResponseEntity<Immobile>(immobileService.save(immobile), HttpStatus.OK);
	}
}
