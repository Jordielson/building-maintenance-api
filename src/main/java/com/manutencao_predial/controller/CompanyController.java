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

import com.manutencao_predial.model.Company;
import com.manutencao_predial.repository.CompanyRepository;

@RestController
@RequestMapping(value="/building-maintenance")
public class CompanyController {
	@Autowired
	CompanyRepository companyRepository;
	
	@GetMapping("/companies")
	public ResponseEntity<List<Company>> getCompanies() {
		List<Company> companies = companyRepository.findAll();
		for (Company c : companies) {
			String id = c.getCnpj();
			c.add(linkTo(methodOn(CompanyController.class).getCompany(id)).withSelfRel());
		}
		return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
	}
	
	@GetMapping("/company/{id}")
	public ResponseEntity<Company> getCompany(@PathVariable(value="id") String cnpj) {
		Optional<Company> compO = companyRepository.findById(cnpj);
		if(!compO.isPresent()) {
			return new ResponseEntity<Company>(HttpStatus.NOT_FOUND);
		} else {
			compO.get().add(linkTo(methodOn(UserController.class).getAll()).withRel("Companies list"));
			return new ResponseEntity<Company>(compO.get(), HttpStatus.OK);
		}
	}
	
	@PostMapping("/company")
	public ResponseEntity<Company> saveUser(@RequestBody Company company) {
		return new ResponseEntity<Company>(companyRepository.save(company), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/company/{cpf}")
	public ResponseEntity<?> deleteUser(@PathVariable(value="id") String cpf) {
		Optional<Company> compO = companyRepository.findById(cpf);
		if(!compO.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			companyRepository.delete(compO.get());
			return new ResponseEntity<>(HttpStatus.OK);		
		}
	}
	
	@PutMapping("/company")
	public ResponseEntity<Company> updateUser(@RequestBody Company company) {
		return new ResponseEntity<Company>(companyRepository.save(company), HttpStatus.OK);
	}
	
}
