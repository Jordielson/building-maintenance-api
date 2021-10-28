package com.manutencao_predial.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.manutencao_predial.model.User;
import com.manutencao_predial.service.UserService;

@SpringBootTest
public class UserTest {
	
	@Autowired
	UserService userService;
	@Test
	public void userUnderAgeTest() {
		LocalDate ld = LocalDate.of(2003, 10, 30);
		Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
		User user = new User();
		user.setDate(date);
		assertThrows(RuntimeException.class, () -> userService.save(user));
	}

}
