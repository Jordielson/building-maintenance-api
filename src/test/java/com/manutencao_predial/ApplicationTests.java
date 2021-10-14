package com.manutencao_predial;

import com.manutencao_predial.service.ServiceService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {
	@Autowired
	ServiceService serviceService;

	@Test
	void contextLoads() {

	}
}
