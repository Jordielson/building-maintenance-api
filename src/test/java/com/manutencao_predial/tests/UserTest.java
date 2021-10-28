package com.manutencao_predial.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.manutencao_predial.model.User;
import com.manutencao_predial.service.EmailService;
import com.manutencao_predial.service.UserService;

@SpringBootTest
public class UserTest {
	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;

	private User user;

	@BeforeEach
	void init() {
		this.user = new User();
		user.setCpf("979.797.949-94");
		user.setName("Joao");
		user.setEmail("joao@gmail.com");
		user.setPassword("123456");
		user.setJob("Prestador");
	}

	@Test
	public void userUnderAgeTest() {
		LocalDate today = LocalDate.now();
		LocalDate ld = LocalDate.of(
			today.getYear() - 18, 
			today.getMonthValue(), 
			today.getDayOfMonth() + 1
		);

		user.setDate(ld);
		assertThrows(RuntimeException.class, () -> userService.save(user));
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
		assertEquals(this.user, userService.save(this.user));
	}

	@Test
	public void userBirthdayTest() {
		LocalDate today = LocalDate.now();
		
		this.user.setDate(LocalDate.of(1990, today.getMonthValue(), today.getDayOfMonth()));
		this.user = userService.save(user);

		assertTrue(emailService.birthdayVerification().stream().anyMatch(item -> user.equals(item)));
		
	}

	@Test
	public void userBirthdayAnotherDayTest() {
		LocalDate today = LocalDate.now();
		int year = 1990;

		this.user.setDate(LocalDate.ofYearDay(year, today.getDayOfYear() - 1));
		this.user = userService.save(user);

		assertFalse(emailService.birthdayVerification().stream().anyMatch(item -> user.equals(item)));

		this.user.setDate(LocalDate.ofYearDay(year, today.getDayOfYear() + 1));
		this.user = userService.save(user);

		assertFalse(emailService.birthdayVerification().stream().anyMatch(item -> user.equals(item)));
	}

}
