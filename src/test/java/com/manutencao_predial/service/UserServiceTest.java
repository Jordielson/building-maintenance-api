package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.manutencao_predial.model.User;

@SpringBootTest
public class UserServiceTest {
	@Mock
	UserService mock;
	@Autowired
	EmailService emailService;

	private User user;

	@BeforeEach
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
		when(mock.save(user)).thenReturn(user);
		assertEquals(user, mock.save(user));
	}

	@Test
	public void userBirthdayTest() {
		LocalDate today = LocalDate.now();
		
		user.setDate(LocalDate.of(1990, today.getMonthValue(), today.getDayOfMonth()));

		List<User> users = new ArrayList<>();
		users.add(user);
		when(mock.findAll()).thenReturn(users);

		assertTrue(emailService.birthdayVerification(mock.findAll()).stream().anyMatch(item -> user.equals(item)));
		verify(mock).findAll();
	}

	@Test
	public void userBirthdayAnotherDayTest() {
		LocalDate today = LocalDate.now();
		int year = 1990;

		user.setDate(LocalDate.ofYearDay(year, today.getDayOfYear() - 1));

		List<User> users = new ArrayList<>();
		users.add(user);
		when(mock.findAll()).thenReturn(users);

		assertFalse(emailService.birthdayVerification(mock.findAll()).stream().anyMatch(item -> user.equals(item)));

		user.setDate(LocalDate.ofYearDay(year, today.getDayOfYear() + 1));

		assertFalse(emailService.birthdayVerification(mock.findAll()).stream().anyMatch(item -> user.equals(item)));
	}

}
