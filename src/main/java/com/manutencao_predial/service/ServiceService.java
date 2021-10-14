package com.manutencao_predial.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.manutencao_predial.exceptions.NotFoundException;
import com.manutencao_predial.exceptions.ServiceNotFoundException;
import com.manutencao_predial.exceptions.UserNotFoundException;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.StateService;
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
            service.setState(StateService.EXECUTANDO);
        }
        return service;
    }

    /**
     * Calcula o valor total das desepesas de uma lista de servicos
     * @param services lista com os servicos a ser calculado o orcamento total
     * @return valor total de todas as despesas da lista "services"
     */
    public BigDecimal calculateTotalBudget(List<Service> services) {
        BigDecimal totalValue = new BigDecimal(0);
        for (Service service : services) {
            totalValue.add(service.getBudget());
        }
        return totalValue;
    }

    /**
     * Calcula o valor total das despesas dos servicos iniciados no mes atual
     * @return valor total das despesas
     */
    public BigDecimal calculateTotalBudgetThisMonth() {
        BigDecimal totalValue = new BigDecimal(0);
        List<Service> servicesThisMonth = findServiceThisMonth();
        for (Service service : servicesThisMonth) {
            totalValue.add(service.getBudget());
        }
        return totalValue;
    }


    /**
     * Encontra todos os servicos iniciados no mes atual
     * @return lista dos servicos iniciados no mes atual
     */
    public List<Service> findServiceThisMonth() {
        LocalDate today = LocalDate.now();
        List<Service> allServices = findAll();
        List<Service> servicesThisMonth = new ArrayList<>();
        for (Service service : allServices) {
            if(service.getInitDate().getYear() == today.getYear() 
                && service.getInitDate().getMonthValue() == today.getMonthValue()){
                    servicesThisMonth.add(service);
            }
        }
        return servicesThisMonth;
    }
    /**
     * Busca todos os servicos de um gerente 
     * @param cpf cpf do gerente
     * @return lista de servico que possuem o cpf do gerente
     */
    public List<Service> findServices(String cpf) {
        return serviceRepository.findServices(cpf);
    }

    /**
     * Busca todos os servicos ordenados pelo estado (iniciado, executando e finalizado)
     * @return lista de servicos ordenado pelo estado de execução do servico
     */
    public List<Service> findAllOrderByState() {
        return serviceRepository.findAllOrderByState();
    }



    public List<Service> findAll() {
        return serviceRepository.findAll();
    }
    public List<Service> findNotifications() {
        return serviceRepository.findNotifications();
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
