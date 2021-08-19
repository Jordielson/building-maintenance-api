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

import com.manutencao_predial.model.Login;
import com.manutencao_predial.model.User;
import com.manutencao_predial.repository.UserRepository;

@RestController
@RequestMapping(value = "/building-maintenance")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userRepository.findAll();
		for (User u : users) {
			String id = u.getCpf();
			u.add(linkTo(methodOn(UserController.class).getUser(id)).withSelfRel());
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable(value="id") String id) {
		Optional<User> userO = userRepository.findById(id);
		if(!userO.isPresent()) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} else {
			userO.get().add(linkTo(methodOn(UserController.class).getAll()).withRel("Users list"));
			return new ResponseEntity<User>(userO.get(), HttpStatus.OK);
		}
	}
	

	@PostMapping("/user")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") String cpf) {
		Optional<User> userO = userRepository.findById(cpf);
		if(!userO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userRepository.delete(userO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/user")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<User> login(@RequestBody Login data) {
		User u = userRepository.login(data.getEmail(), data.getPassword());
		if(u == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		} else {
			u.add(linkTo(methodOn(UserController.class).getAll()).withRel("Users list"));
			return new ResponseEntity<User>(u, HttpStatus.OK);
		}
	}
}
