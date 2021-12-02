package com.manutencao_predial.resouce;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.StateService;
import com.manutencao_predial.service.ServiceService;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
public class ServiceResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceService serviceService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void  createServiceTest() throws Exception{
        Service service = new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.INICIADO, null);

        String jsonService = objectMapper.writeValueAsString(service);

        Mockito.when(serviceService.save(any()))
            .thenReturn(service);

        mockMvc.perform(
            MockMvcRequestBuilders.post("/ManuPredSoft/service")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonService)
        )
        .andExpect(status().isCreated());
        // .andExpect(header().exists("name"));

        ArgumentCaptor<Service> captor = ArgumentCaptor.forClass(Service.class);
        verify(serviceService).save(captor.capture());
        Service serviceCaptured = captor.getValue();

        assertEquals(service.getId(), serviceCaptured.getId());
        assertEquals(service.getTitle(), serviceCaptured.getTitle());
        assertEquals(service.getBudget(), serviceCaptured.getBudget());
        assertEquals(service.getDescription(), serviceCaptured.getDescription());
        assertEquals(service.getState(), serviceCaptured.getState());
        assertEquals(service.getRoom(), serviceCaptured.getRoom());
    }

    @Test
    public void getServiceByIdTest() throws Exception {
        Service service = new Service(1, "Trocar Lampada", new BigDecimal(20), "Trocar Lampada da sala 1", StateService.INICIADO, null);

        Optional<Service> serviceOpt = Optional.of(service);

        when(serviceService.findById(eq(1)))
            .thenReturn(serviceOpt);

        mockMvc.perform(
            MockMvcRequestBuilders.get("/ManuPredSoft/service/" + service.getId())
            .accept(MediaType.APPLICATION_JSON)
        )

        .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(service.getTitle()))
            .andExpect(jsonPath("$.description", Matchers.is(service.getDescription())))
            .andExpect(jsonPath("$.budget").value(service.getBudget()))
            .andExpect(jsonPath("$.state", Matchers.is(service.getState().toString())))
            .andExpect(jsonPath("$.room", Matchers.is(service.getRoom())));
    }
    
}
