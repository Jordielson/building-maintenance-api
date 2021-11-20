package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.MaterialService;
import com.manutencao_predial.repository.MaterialServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceService {
    @Autowired
    MaterialServiceRepository materialServiceRepository;

    public List<MaterialService> findAll() {
        return materialServiceRepository.findAll();
    }

    public Optional<MaterialService> findById(int id) {
        Optional<MaterialService> materialServiceO = materialServiceRepository.findById(id);
		return materialServiceO;
    }

    public MaterialService save(MaterialService materialService) {
        return materialServiceRepository.save(materialService);
    }

    public MaterialService update(MaterialService materialService) {
        return materialServiceRepository.save(materialService);
    }

    public void delete(MaterialService materialService) {
        materialServiceRepository.delete(materialService);
    }
}
