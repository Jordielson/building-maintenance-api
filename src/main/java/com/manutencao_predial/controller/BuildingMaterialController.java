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

import com.manutencao_predial.model.BuildingMaterial;
import com.manutencao_predial.repository.BuildingMaterialRepository;

@RestController
@RequestMapping(value="/ManuPredSoft")
public class BuildingMaterialController {

	@Autowired
	BuildingMaterialRepository buildingMaterialRepository;
	
	@GetMapping("/building-material")
	public ResponseEntity<List<BuildingMaterial>> getAll() {
		List<BuildingMaterial> materialList = buildingMaterialRepository.findAll();
		for (BuildingMaterial m : materialList) {
			int id = m.getId();
			m.add(linkTo(methodOn(BuildingMaterialController.class).getMaterial(id)).withSelfRel());
		}
		return new ResponseEntity<List<BuildingMaterial>>(materialList, HttpStatus.OK);
	}
	
	@GetMapping("/building-material/{id}")
	public ResponseEntity<BuildingMaterial> getMaterial(@PathVariable(value="id") int id) {
		Optional<BuildingMaterial> matO = buildingMaterialRepository.findById(id);
		if(!matO.isPresent()) {
			return new ResponseEntity<BuildingMaterial>(HttpStatus.NOT_FOUND);
		} else {
			matO.get().add(linkTo(methodOn(BuildingMaterialController.class).getAll()).withRel("Building material list"));
			return new ResponseEntity<BuildingMaterial>(matO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/building-material")
	public ResponseEntity<BuildingMaterial> saveMaterial(@RequestBody BuildingMaterial material) {
		return new ResponseEntity<BuildingMaterial>(buildingMaterialRepository.save(material), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/building-material/{id}")
	public ResponseEntity<?> deleteMaterial(@PathVariable(value="id") int id) {
		Optional<BuildingMaterial> matO = buildingMaterialRepository.findById(id);
		if(!matO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			buildingMaterialRepository.delete(matO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/building-material")
	public ResponseEntity<BuildingMaterial> updateMaterial(@RequestBody BuildingMaterial material) {
		return new ResponseEntity<BuildingMaterial>(buildingMaterialRepository.save(material), HttpStatus.OK);
	}
}
