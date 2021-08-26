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

import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.User;
import com.manutencao_predial.repository.ServiceRepository;

@RestController
@RequestMapping(value="/building-maintenance")
public class ServiceController {

	@Autowired
	ServiceRepository serviceRepository;
	
	@GetMapping("/service")
	public ResponseEntity<List<Service>> getAll() {
		List<Service> serviceList = serviceRepository.findAllOrderByState();
		for (Service service : serviceList) {
			int id = service.getId();
			service.add(linkTo(methodOn(ServiceController.class).getService(id)).withSelfRel());
		}
		return new ResponseEntity<List<Service>>(serviceList, HttpStatus.OK);
	}
	
	@GetMapping("/service/{id}")
	public ResponseEntity<Service> getService(@PathVariable(value="id") int id) {
		Optional<Service> serviceO = serviceRepository.findById(id);
		if(!serviceO.isPresent()) {
			return new ResponseEntity<Service>(HttpStatus.NOT_FOUND);
		} else {
			serviceO.get().add(linkTo(methodOn(ServiceController.class).getAll()).withRel("Service list"));
			return new ResponseEntity<Service>(serviceO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/service")
	public ResponseEntity<Service> saveService(@RequestBody Service service) {
		return new ResponseEntity<Service>(serviceRepository.save(service), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/service/{id}")
	public ResponseEntity<?> deleteService(@PathVariable(value="id") int id) {
		Optional<Service> serviceO = serviceRepository.findById(id);
		if(!serviceO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			serviceRepository.delete(serviceO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/service")
	public ResponseEntity<Service> updateService(@RequestBody Service service) {
		return new ResponseEntity<Service>(serviceRepository.save(service), HttpStatus.OK);
	}

	@GetMapping("/service/notifications")
	public ResponseEntity<List<Service>> getNotifications() {
		List<Service> serviceList = serviceRepository.findNotifications();
		for (Service service : serviceList) {
			int id = service.getId();
			service.add(linkTo(methodOn(ServiceController.class).getService(id)).withSelfRel());
		}
		return new ResponseEntity<List<Service>>(serviceList, HttpStatus.OK);
	}

	@PostMapping("/services")
	public ResponseEntity<List<Service>> getAll(@RequestBody User user) {
		List<Service> serviceList = serviceRepository.findServices(user.getCpf());
		for (Service service : serviceList) {
			int id = service.getId();
			service.add(linkTo(methodOn(ServiceController.class).getService(id)).withSelfRel());
		}
		return new ResponseEntity<List<Service>>(serviceList, HttpStatus.OK);
	}
}
