package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.manutencao_predial.model.Immobile;
import com.manutencao_predial.model.ImmobileSizeEnum;
import com.manutencao_predial.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImmobileTest {
    @Autowired
    ImmobileService immobileService;
    @Autowired
    UserService userService;

    private static Immobile immobile;

    @BeforeAll
    static void initImmobile() {
        immobile = new Immobile();
        immobile.setId(1);
        immobile.setAddress("Rua: Serverino Leite nº 1203");
        immobile.setName("Comdominio Leite");
    }

    @AfterEach
    void dropEmployees() {
        immobile.setEmployees(new ArrayList<>());
        immobileService.save(immobile);
    }
    
    @Test
    public void limitMaxEmployeeByImmobileSMALL() {
        immobile.setImmobileSize(ImmobileSizeEnum.SMALL_SIZE);
        List<User> employees = new ArrayList<>();
        immobile.setEmployees(employees);

        User user1 = userService.findById("619.497.979-79").get();
        employees.add(user1);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user1));

        User user2 =  userService.findById("979.797.949-94").get();
        employees.add(user2);
        assertThrows(RuntimeException.class, () -> immobileService.save(immobile));

        employees.remove(user2);
        User user3 = userService.findById("154.554.646-64").get();
        employees.add(user3);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user3));
    }

    @Test
    public void limitMaxEmployeeByImmobileMID() {
        immobile.setImmobileSize(ImmobileSizeEnum.MID_SIZE);
        List<User> employees = new ArrayList<>();
        immobile.setEmployees(employees);

        User user1 = userService.findById("619.497.979-79").get();
        employees.add(user1);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user1));

        User user2 =  userService.findById("979.797.949-94").get();
        employees.add(user2);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user2));

        User user3 =  userService.findById("979.797.949-62").get();
        employees.add(user3);
        assertThrows(RuntimeException.class, () -> immobileService.save(immobile));

        employees.remove(user3);
        User user4 = userService.findById("619.497.979-80").get();
        employees.add(user4);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user4));
    }

    @Test
    public void limitMaxEmployeeByImmobileLARGE() {
        immobile.setImmobileSize(ImmobileSizeEnum.LARGE_SIZE);
        List<User> employees = new ArrayList<>();
        immobile.setEmployees(employees);

        User user1 = userService.findById("619.497.979-79").get();
        employees.add(user1);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user1));

        User user2 =  userService.findById("979.797.949-94").get();
        employees.add(user2);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user2));

        User user3 =  userService.findById("979.797.949-62").get();
        employees.add(user3);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user3));

        User user4 =  userService.findById("979.797.949-95").get();
        employees.add(user4);
        assertThrows(RuntimeException.class, () -> immobileService.save(immobile));

        employees.remove(user4);
        User user5 = userService.findById("619.497.979-80").get();
        employees.add(user5);
        assertTrue(immobileService.save(immobile).getEmployees().contains(user5));
    }
}
