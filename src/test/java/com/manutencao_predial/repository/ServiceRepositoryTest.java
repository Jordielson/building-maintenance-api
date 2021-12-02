
package com.manutencao_predial.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.StateService;
import com.manutencao_predial.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ServiceRepositoryTest
 */
@SpringBootTest
public class ServiceRepositoryTest {
    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UserRepository userRepository;

    List<Service> services = new ArrayList<>();

    @Test
    public void findNotificationsTest() throws Exception {
        User gerente = new User("979.797.949-94", "Joao", "joao@gmail.com", "123456", "Gerente");
        userRepository.save(gerente);

        Service service = new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.INICIADO, null);
        serviceRepository.save(service);

        Service service2 = new Service(2, "Trocar Lampada 2", new BigDecimal(30), "Trocar Lampada da sala 2", StateService.FINALIZADO, null);
        serviceRepository.save(service2);
        
        Service service3 = new Service(3, "Trocar Lampada 3", new BigDecimal(40), "Trocar Lampada da sala 3", StateService.INICIADO, null);
        service3.setManager(gerente);
        serviceRepository.save(service3);


        List<Service> notifications = serviceRepository.findNotifications();
        
        assertEquals(2, notifications.size());
        assertTrue(notifications.contains(service));

        Service serviceFound = notifications.stream().filter((item -> item.equals(service))).findFirst().orElse(new Service());

        assertEquals(service, serviceFound);
        assertNotEquals(serviceFound.getBudget(), null);
        assertEquals(serviceFound.getTitle(), "Trocar Lampada");
        assertEquals(serviceFound.getDescription(), "Trocar Lampada da sala 1");
        assertEquals(serviceFound.getState(), StateService.INICIADO);
        assertEquals(serviceFound.getRoom(), null);
    }

    @Test
    public void findServicesByManegerTest() {
        User gerente = new User("979.797.949-94", "Joao", "joao@gmail.com", "123456", "Gerente");
        userRepository.save(gerente);

        Service serviceSemGerente = new Service(1, "Trocar Lampada 1", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.INICIADO, null);
        serviceRepository.save(serviceSemGerente);

        Service serviceComGerente = new Service(2, "Trocar Lampada 2", new BigDecimal(30), "Trocar Lampada da sala 2", StateService.INICIADO, null);
        serviceComGerente.setManager(gerente);
        serviceRepository.save(serviceComGerente);


        List<Service> services = serviceRepository.findServices(gerente.getCpf());
        
        // Service com Gerente
        Service serviceFound = services.stream().filter((item -> item.equals(serviceComGerente))).findFirst().orElse(null);

        assertNotEquals(serviceFound, null);
        assertEquals(serviceFound, serviceComGerente);
        assertEquals(serviceFound.getBudget().doubleValue(), serviceComGerente.getBudget().doubleValue());
        assertEquals(serviceFound.getTitle(), serviceComGerente.getTitle());
        assertEquals(serviceFound.getDescription(), serviceComGerente.getDescription());
        assertEquals(serviceFound.getState(), serviceComGerente.getState());
        assertEquals(serviceFound.getRoom(), serviceComGerente.getRoom());
        
        // Service Sem gerente
        assertFalse(services.contains(serviceSemGerente));
    }

    @Test
    public void findServiceByStateTest() {
        Service serviceExecutando = new Service(1, "Trocar Lampada 1", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.EXECUTANDO, null);
        serviceRepository.save(serviceExecutando);

        Service serviceFinalizado = new Service(2, "Trocar Lampada 2", new BigDecimal(30), "Trocar Lampada da sala 2", StateService.FINALIZADO, null);
        serviceRepository.save(serviceFinalizado);
        
        Service serviceIniciado = new Service(3, "Trocar Lampada 3", new BigDecimal(40), "Trocar Lampada da sala 3", StateService.INICIADO, null);
        serviceRepository.save(serviceIniciado);

        List<Service> services = serviceRepository.findAllOrderByState();

        assertEquals(services.size(), 3);
        assertEquals(services.get(0), serviceExecutando);
        assertEquals(services.get(1), serviceFinalizado);
        assertEquals(services.get(2), serviceIniciado);
    }
    
}