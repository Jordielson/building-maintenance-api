package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.Service;
import com.manutencao_predial.repository.ServiceRepository;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    ServiceRepository serviceRepository;

    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    public List<Service> findAllOrderByState() {
        return serviceRepository.findAllOrderByState();
    }

    public List<Service> findNotifications() {
        return serviceRepository.findNotifications();
    }

    public List<Service> findServices(String cpf) {
        return serviceRepository.findServices(cpf);
    }

    public Optional<Service> findById(int id) {
        Optional<Service> serviceO = serviceRepository.findById(id);
		return serviceO;
    }

    public Service save(Service service) {
        return serviceRepository.save(service);
    }

    public Service update(Service service) {
        return serviceRepository.save(service);
    }

    public void delete(Service service) {
        serviceRepository.delete(service);
    }


}
