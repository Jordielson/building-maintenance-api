package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.manutencao_predial.model.Immobile;
import com.manutencao_predial.model.ImmobileSizeEnum;
import com.manutencao_predial.model.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ImmobileServiceTest {
    @Mock
    ImmobileService mock;
    @Mock
    ImmobileService mock2;
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
    }
    
    @Test
    public void limitMaxEmployeeByImmobileSMALL() {
        when(mock.save(immobile)).thenReturn(immobile);
        when(mock2.save(immobile)).thenThrow(RuntimeException.class);

        immobile.setImmobileSize(ImmobileSizeEnum.SMALL_SIZE);
        List<User> employees = new ArrayList<>();
        immobile.setEmployees(employees);

        //User1 - Chefe de Setor
        User user1 = userService.findById("619.497.979-79").get();
        employees.add(user1);

        assertTrue(mock.save(immobile).getEmployees().contains(user1));

        //User2 - Chefe de Setor
        User user2 =  userService.findById("979.797.949-94").get();
        employees.add(user2);
        
        assertThrows(RuntimeException.class, () -> mock2.save(immobile));
        
        employees.remove(user2);
        //User3 - Prestador
        User user3 = userService.findById("154.554.646-64").get();
        employees.add(user3);
        assertTrue(mock.save(immobile).getEmployees().contains(user3));

        verify(mock, times(2)).save(immobile);
        verify(mock2).save(immobile);
    }

    @Test
    public void limitMaxEmployeeByImmobileMID() {
        when(mock.save(immobile)).thenReturn(immobile);
        when(mock2.save(immobile)).thenThrow(RuntimeException.class);

        immobile.setImmobileSize(ImmobileSizeEnum.MID_SIZE);
        List<User> employees = new ArrayList<>();
        immobile.setEmployees(employees);

        User user1 = userService.findById("619.497.979-79").get();
        employees.add(user1);
        assertTrue(mock.save(immobile).getEmployees().contains(user1));

        User user2 =  userService.findById("979.797.949-94").get();
        employees.add(user2);
        assertTrue(mock.save(immobile).getEmployees().contains(user2));

        User user3 =  userService.findById("979.797.949-62").get();
        employees.add(user3);
        assertThrows(RuntimeException.class, () -> mock2.save(immobile));

        employees.remove(user3);
        User user4 = userService.findById("154.554.646-64").get();
        employees.add(user4);
        assertTrue(mock.save(immobile).getEmployees().contains(user4));

        verify(mock, times(3)).save(immobile);
        verify(mock2).save(immobile);
    }

    @Test
    public void limitMaxEmployeeByImmobileLARGE() {
        when(mock.save(immobile)).thenReturn(immobile);
        when(mock2.save(immobile)).thenThrow(RuntimeException.class);

        immobile.setImmobileSize(ImmobileSizeEnum.LARGE_SIZE);
        List<User> employees = new ArrayList<>();
        immobile.setEmployees(employees);

        User user1 = userService.findById("619.497.979-79").get();
        employees.add(user1);
        assertTrue(mock.save(immobile).getEmployees().contains(user1));

        User user2 =  userService.findById("979.797.949-94").get();
        employees.add(user2);
        assertTrue(mock.save(immobile).getEmployees().contains(user2));

        User user3 =  userService.findById("979.797.949-62").get();
        employees.add(user3);
        assertTrue(mock.save(immobile).getEmployees().contains(user3));

        User user4 =  userService.findById("979.797.949-95").get();
        employees.add(user4);
        assertThrows(RuntimeException.class, () -> mock2.save(immobile));

        employees.remove(user4);
        User user5 = userService.findById("154.554.646-64").get();
        employees.add(user5);
        assertTrue(mock.save(immobile).getEmployees().contains(user5));

        verify(mock, times(4)).save(immobile);
        verify(mock2).save(immobile);
    }
}
