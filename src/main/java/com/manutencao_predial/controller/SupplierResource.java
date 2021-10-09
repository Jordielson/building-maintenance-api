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

import com.manutencao_predial.model.Supplier;
import com.manutencao_predial.service.SupplierService;

@RestController
@RequestMapping(value="/ManuPredSoft")
public class SupplierResource {

	@Autowired
	SupplierService supplierService;
	
	@GetMapping("/supplier")
	public ResponseEntity<List<Supplier>> getAll() {
		List<Supplier> supplier = supplierService.findAll();
		for (Supplier s : supplier) {
			String id = s.getCnpj();
			s.add(linkTo(methodOn(SupplierResource.class).getSupplier(id)).withSelfRel());
		}
		return new ResponseEntity<List<Supplier>>(supplier, HttpStatus.OK);
	}
	
	@GetMapping("/supplier/{id}")
	public ResponseEntity<Supplier> getSupplier(@PathVariable(value="id") String id) {
		Optional<Supplier> supplierO = supplierService.findById(id);
		if(!supplierO.isPresent()) {
			return new ResponseEntity<Supplier>(HttpStatus.NOT_FOUND);
		} else {
			supplierO.get().add(linkTo(methodOn(SupplierResource.class).getAll()).withRel("Supplier list"));
			return new ResponseEntity<Supplier>(supplierO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/supplier")
	public ResponseEntity<Supplier> saveSupplier(@RequestBody Supplier supplier) {
		return new ResponseEntity<Supplier>(supplierService.save(supplier), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/supplier/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable(value="id") String id) {
		Optional<Supplier> supplierO = supplierService.findById(id);
		if(!supplierO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			supplierService.delete(supplierO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/supplier")
	public ResponseEntity<Supplier> updateSupplier(@RequestBody Supplier supplier) {
		return new ResponseEntity<Supplier>(supplierService.save(supplier), HttpStatus.OK);
	}
}
