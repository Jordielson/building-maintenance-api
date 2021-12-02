
package com.manutencao_predial.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.StateService;
import com.manutencao_predial.service.RoomService;
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
    RoomService roomService;

    List<Service> services = new ArrayList<>();

    @Test
    public void findNotificationsTest() throws Exception {
        Service service = new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.INICIADO, null);
        serviceRepository.save(service);

        List<Service> notifications = serviceRepository.findNotifications();
        
        assertEquals(1, notifications.size());
        assertTrue(notifications.contains(service));

        Service serviceFound = notifications.stream().filter((item -> item.equals(service))).findFirst().orElse(new Service());

        assertEquals(service, serviceFound);
        assertNotEquals(serviceFound.getBudget(), null);
        assertEquals(serviceFound.getTitle(), "Trocar Lampada");
        assertEquals(serviceFound.getDescription(), "Trocar Lampada da sala 1");
        assertEquals(serviceFound.getState(), StateService.INICIADO);
        assertEquals(serviceFound.getRoom(), null);
    }
    
}