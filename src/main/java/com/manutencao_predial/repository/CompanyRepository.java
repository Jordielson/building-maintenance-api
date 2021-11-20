package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manutencao_predial.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>{

}
