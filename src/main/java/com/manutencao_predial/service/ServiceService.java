package com.manutencao_predial.service;

import java.util.List;
import java.util.Optional;

import com.manutencao_predial.exceptions.NotFoundException;
import com.manutencao_predial.exceptions.ServiceNotFoundException;
import com.manutencao_predial.exceptions.UserNotFoundException;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.User;
import com.manutencao_predial.repository.ServiceRepository;
import com.manutencao_predial.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class ServiceService {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired 
    UserRepository userRepository;

    /**
     * Adiciona um prestador ao servico, 
     * caso seja o primeiro prestador a ser adicionado ao servico 
     * o estado do servico deverá mudar do estado Iniciado para Executando
     * @param idService id do servico que se deseja adicionar o prestador
     * @param email email do prestador
     * @throws NotFoundException quando nao foi encontrado o servico ou o prestador
     */
    public Service addProvider(int idService, String email) throws NotFoundException {
        Optional<Service> serviceO = findById(idService);
        if(!serviceO.isPresent()) {
            throw new ServiceNotFoundException("Servico não encontrado!");
        } 
        User user = userRepository.login(email);
        if(user == null) {
            throw new UserNotFoundException("Usuario nao encontrado!");
        }
        Service service = serviceO.get();
        service.addProvider(user);
        if(service.getProviders().size() == 1) {
            service.setState("Executando");
        }
        save(service);
        return service;
    }

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
