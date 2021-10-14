package com.manutencao_predial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	public void testbasic() {
		assertEquals(3, 3);
	}
}
