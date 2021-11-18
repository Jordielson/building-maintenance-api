package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.manutencao_predial.model.User;

@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class UserTest {
	@Mock
	UserService mock;
	@Autowired
	EmailService emailService;

	private User user;

	@BeforeAll
	void initUserTest() {
		user = new User();
		user.setCpf("979.797.949-94");
		user.setName("Joao");
		user.setEmail("joao@gmail.com");
		user.setPassword("123456");
		user.setJob("Prestador");
	}

	@Test
	public void userUnderAgeTest() {
		LocalDate today = LocalDate.now();
		LocalDate ld = LocalDate.ofYearDay(today.getYear() - 18, today.getDayOfYear() + 1);
        user.setDate(ld);
		
		when(mock.save(user)).thenThrow(new RuntimeException("Usuario deve ter no minimo 18 anos"));
        assertThrows(RuntimeException.class, () -> mock.save(user));
        verify(mock).save(user);
	}

	@Test
	public void userAdultTest() {
		LocalDate today = LocalDate.now();
		LocalDate ld = LocalDate.of(
			today.getYear() - 18, 
			today.getMonthValue(), 
			today.getDayOfMonth()
		);

		user.setDate(ld);
		assertEquals(user, mock.save(user));
	}

	@Test
	public void userBirthdayTest() {
		LocalDate today = LocalDate.now();
		
		user.setDate(LocalDate.of(1990, today.getMonthValue(), today.getDayOfMonth()));
		user = mock.save(user);

		assertTrue(emailService.birthdayVerification().stream().anyMatch(item -> user.equals(item)));
	}

	@Test
	public void userBirthdayAnotherDayTest() {
		LocalDate today = LocalDate.now();
		int year = 1990;

		user.setDate(LocalDate.ofYearDay(year, today.getDayOfYear() - 1));
		user = mock.save(user);

		assertFalse(emailService.birthdayVerification().stream().anyMatch(item -> user.equals(item)));

		user.setDate(LocalDate.ofYearDay(year, today.getDayOfYear() + 1));
		user = mock.save(user);

		assertFalse(emailService.birthdayVerification().stream().anyMatch(item -> user.equals(item)));
	}

}
