package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.manutencao_predial.model.Room;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.StateService;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RoomServiceTest {
    @Autowired
    RoomService roomService;
    @Mock
    ServiceService serviceService;

    @Test
    public void testFindRoomsInMaintenance() {
        Room room = roomService.findById(1).get();
        Room room2 = roomService.findById(2).get();
        Room room3 = roomService.findById(3).get();
        List<Service> services = new ArrayList<>();
        services.add(new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.EXECUTANDO, room));
        services.add(new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 2", StateService.INICIADO, room2));
        services.add(new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 3", StateService.FINALIZADO, room3));

        when(serviceService.findAll()).thenReturn(services);

        assertTrue(roomService.findRoomsInMaintenance(serviceService.findAll()).contains(room));
        assertFalse(roomService.findRoomsInMaintenance(serviceService.findAll()).contains(room2));
        assertFalse(roomService.findRoomsInMaintenance(serviceService.findAll()).contains(room3));
        verify(serviceService, times(3)).findAll();
    }
}
