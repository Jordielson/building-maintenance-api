package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Immobile;
import com.manutencao_predial.repository.ImmobileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImmobileService {
    @Autowired
    ImmobileRepository immobileRepository;

    public List<Immobile> findAll() {
        return immobileRepository.findAll();
    }

    public Optional<Immobile> findById(int id) {
        Optional<Immobile> immobileO = immobileRepository.findById(id);
		return immobileO;
    }

    public Immobile save(Immobile immobile) {
        return immobileRepository.save(immobile);
    }

    public Immobile update(Immobile immobile) {
        return immobileRepository.save(immobile);
    }

    public void delete(Immobile immobile) {
        immobileRepository.delete(immobile);
    }
}
