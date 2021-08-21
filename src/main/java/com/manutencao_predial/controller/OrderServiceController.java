package com.manutencao_predial.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.OrderService;
import com.manutencao_predial.repository.OrderServiceRepository;

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

@RestController
@RequestMapping(value="/building-maintenance")
public class OrderServiceController {
    
    @Autowired
	OrderServiceRepository orderServiceRepository;
	
	@GetMapping("/order-service")
	public ResponseEntity<List<OrderService>> getAll() {
		List<OrderService> orderService = orderServiceRepository.findAll();
		for (OrderService item : orderService) {
			int id = item.getId();
			item.add(linkTo(methodOn(OrderServiceController.class).getItem(id)).withSelfRel());
		}
		return new ResponseEntity<List<OrderService>>(orderService, HttpStatus.OK);
	}
	
	@GetMapping("/order-service/{id}")
	public ResponseEntity<OrderService> getItem(@PathVariable(value="id") int id) {
		Optional<OrderService> orderO = orderServiceRepository.findById(id);
		if(!orderO.isPresent()) {
			return new ResponseEntity<OrderService>(HttpStatus.NOT_FOUND);
		} else {
			orderO.get().add(linkTo(methodOn(OrderServiceController.class).getAll()).withRel("OrderService list"));
			return new ResponseEntity<OrderService>(orderO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/order-service")
	public ResponseEntity<OrderService> saveItem(@RequestBody OrderService item) {
		return new ResponseEntity<OrderService>(orderServiceRepository.save(item), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/order-service/{id}")
	public ResponseEntity<?> deleteItem(@PathVariable(value="id") int id) {
		Optional<OrderService> orderO = orderServiceRepository.findById(id);
		if(!orderO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			orderServiceRepository.delete(orderO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/order-service")
	public ResponseEntity<OrderService> updateItem(@RequestBody OrderService item) {
		return new ResponseEntity<OrderService>(orderServiceRepository.save(item), HttpStatus.OK);
	}
}
