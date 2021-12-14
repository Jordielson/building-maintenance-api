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

import com.manutencao_predial.util.Login;
import com.manutencao_predial.model.User;
import com.manutencao_predial.service.UserService;

@CrossOrigin(origins = "http://localhost:19006")
@RestController
@RequestMapping(value = "/ManuPredSoft")
public class UserResource {
	@Autowired
	UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAll(){
		List<User> users = userService.findAll();
		for (User u : users) {
			String id = u.getCpf();
			u.add(linkTo(methodOn(UserResource.class).getUser(id)).withSelfRel());
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable(value="id") String id) {
		Optional<User> userO = userService.findById(id);
		if(!userO.isPresent()) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		} else {
			userO.get().add(linkTo(methodOn(UserResource.class).getAll()).withRel("Users list"));
			return new ResponseEntity<User>(userO.get(), HttpStatus.OK);
		}
	}
	

	@PostMapping("/user")
	public ResponseEntity<User> saveUser(@RequestBody User user) {
		return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") String cpf) {
		Optional<User> userO = userService.findById(cpf);
		if(!userO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			userService.delete(userO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/user")
	public ResponseEntity<User> updateUser(@RequestBody User user) {
		return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<?> login(@RequestBody Login data) {
		User u = userService.login(data.getEmail(), data.getPassword());
		if(u == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email nao cadastrado ou Senha invalida!");
		} else {
			return new ResponseEntity<User>(u, HttpStatus.OK);
		}
	}

	@GetMapping("/users/providers")
	public ResponseEntity<List<User>> getProviders(){
		List<User> users = userService.findProviders();
		for (User u : users) {
			String id = u.getCpf();
			u.add(linkTo(methodOn(UserResource.class).getUser(id)).withSelfRel());
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
}
