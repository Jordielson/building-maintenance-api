package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Supplier;
import com.manutencao_predial.repository.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    public List<Supplier> findAll() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> findById(String cnpj) {
        Optional<Supplier> supplierO = supplierRepository.findById(cnpj);
		return supplierO;
    }

    public Supplier save(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier update(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public void delete(Supplier supplier) {
        supplierRepository.delete(supplier);
    }
}
