package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Company;
import com.manutencao_predial.repository.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Optional<Company> findById(String cnpj) {
        Optional<Company> companyO = companyRepository.findById(cnpj);
		return companyO;
    }

    public Company save(Company company) {
        return companyRepository.save(company);
    }

    public Company update(Company company) {
        return companyRepository.save(company);
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }
}
