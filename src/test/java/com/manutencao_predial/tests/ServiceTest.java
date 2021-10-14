package com.manutencao_predial.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.manutencao_predial.exceptions.NotFoundException;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.service.ServiceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
	ServiceService serviceService;

    @Test
	public void checkServiceNotFoundException() {
		assertThrows(NotFoundException.class, () -> serviceService.addProvider(11, "joao@gmail.com"));
	}

    @Test
    public void checkProviderNotFoundException() {
        assertThrows(NotFoundException.class, () -> serviceService.addProvider(1, "jose@gmail.com"));
    }

    @Test
    public void checkNotFoundException() {
        assertThrows(NotFoundException.class, () -> serviceService.addProvider(34, "jose@gmail.com"));
    }

    @Test
    public void checkAddProvider() {
        String email = "joao@gmail.com";
        Service service = serviceService.addProvider(2, email);
        assertTrue(service.getProviders().stream().anyMatch(item -> email.equals(item.getEmail())));
    }
}
