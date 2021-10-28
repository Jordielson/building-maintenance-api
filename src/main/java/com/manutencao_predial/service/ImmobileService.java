package com.manutencao_predial.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Immobile;
import com.manutencao_predial.model.ImmobileSizeEnum;
import com.manutencao_predial.model.User;
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

    public List<User> findHeadOfSector(Immobile immobile) {
        List<User> headOfSectorList = new ArrayList<>();
        List<User> employees = immobile.getEmployees();
        for (User user : employees) {
            if(user.getJob().equals("Chefe de Setor")) {
                headOfSectorList.add(user);
            }
        }
        return headOfSectorList;
    }

    public void checkLimitMaxImmobileSize(Immobile immobile) {
        ImmobileSizeEnum immobileSize = immobile.getImmobileSize();
        List<User> headOfSectorList = findHeadOfSector(immobile);

        if(immobileSize.getEmployees() < headOfSectorList.size()){
            throw new RuntimeException("Limite de funcionarios ultrapassado");
        }
    }

    public Optional<Immobile> findById(int id) {
        Optional<Immobile> immobileO = immobileRepository.findById(id);
		return immobileO;
    }

    public Immobile save(Immobile immobile) {
        checkLimitMaxImmobileSize(immobile);
        return immobileRepository.save(immobile);
    }

    public Immobile update(Immobile immobile) {
        checkLimitMaxImmobileSize(immobile);
        return immobileRepository.save(immobile);
    }

    public void delete(Immobile immobile) {
        immobileRepository.delete(immobile);
    }
}
