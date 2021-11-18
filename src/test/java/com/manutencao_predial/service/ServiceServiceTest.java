package com.manutencao_predial.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.stream.Stream;

import com.manutencao_predial.exceptions.NotFoundException;
import com.manutencao_predial.exceptions.ServiceNotFoundException;
import com.manutencao_predial.exceptions.UserNotFoundException;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.model.User;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceServiceTest {
    @Mock
	ServiceService serviceService;

    @ParameterizedTest(name = "Servico com id {0} esta sendo verificado")
    @ValueSource(ints = {-1, 0, 10, 100, 999})
	public void checkServiceNotFoundException(int idService) {
        String emailProvider = "paulo@gmail.com";
        when(serviceService.addProvider(-1, emailProvider)).thenThrow(ServiceNotFoundException.class);
        when(serviceService.addProvider(0, emailProvider)).thenThrow(ServiceNotFoundException.class);
        when(serviceService.addProvider(10, emailProvider)).thenThrow(ServiceNotFoundException.class);
        when(serviceService.addProvider(100, emailProvider)).thenThrow(ServiceNotFoundException.class);
        when(serviceService.addProvider(999, emailProvider)).thenThrow(ServiceNotFoundException.class);

		assertThrows(NotFoundException.class, () -> serviceService.addProvider(idService, emailProvider));
        verify(serviceService).addProvider(idService, emailProvider);
	}

    @ParameterizedTest(name = "Prestador com email {0} esta sendo verificado")
    @ValueSource(strings = {"jose@gmail.com", "aaaa", "", "/n", " "})
    public void checkProviderNotFoundException(String email) {
        when(serviceService.addProvider(1, "jose@gmail.com")).thenThrow(UserNotFoundException.class);
        when(serviceService.addProvider(1, "aaaa")).thenThrow(UserNotFoundException.class);
        when(serviceService.addProvider(1, "")).thenThrow(UserNotFoundException.class);
        when(serviceService.addProvider(1, "/n")).thenThrow(UserNotFoundException.class);
        when(serviceService.addProvider(1, " ")).thenThrow(UserNotFoundException.class);

        try {
            serviceService.addProvider(1, email);
            fail("Deveria ser lancado a exception");
        } catch (Exception e) {
            verify(serviceService).addProvider(1, email);
        }
    }

    @ParameterizedTest(name = "Servico com id {0} e Prestador com email {1} esta sendo verificado")
    @MethodSource("provideParametersNotFound")
    public void checkNotFoundException(int idService, String email) {
        when(serviceService.addProvider(-1,"jose@gmail.com")).thenThrow(NotFoundException.class);
        when(serviceService.addProvider(-1,"")).thenThrow(NotFoundException.class);
        when(serviceService.addProvider(0,"jose@gmail.com")).thenThrow(NotFoundException.class);
        when(serviceService.addProvider(Integer.MAX_VALUE,"")).thenThrow(NotFoundException.class);
        when(serviceService.addProvider(1,"aaaa")).thenThrow(NotFoundException.class);
        when(serviceService.addProvider(100,"/n")).thenThrow(NotFoundException.class);
        
        assertThrows(NotFoundException.class, () -> serviceService.addProvider(idService, email));
        verify(serviceService).addProvider(idService, email);
    }

    private static Stream<Arguments> provideParametersNotFound() {
        return Stream.of(
                Arguments.of(-1, "jose@gmail.com"),
                Arguments.of(-1, ""),
                Arguments.of(0, "jose@gmail.com"),
                Arguments.of(Integer.MAX_VALUE, ""),
                Arguments.of(1, "aaaa"),
                Arguments.of(100, "/n")
        );
    }

    @ParameterizedTest(name = "Servico com id {0} e Prestador com email {1} esta sendo verificado")
    @MethodSource("provideParametersValid")
    public void checkAddProvider(int idService, String email) {
        User user = new User();
        user.setEmail(email);
        Service service = new Service();
        service.setId(idService);
        service.addProvider(user);
        
        when(serviceService.addProvider(idService, email)).thenReturn(service);
        
        Service returnedService = serviceService.addProvider(idService, email);
        assertTrue(returnedService.getProviders().stream().anyMatch(item -> email.equals(item.getEmail())));
        verify(serviceService).addProvider(idService, email);
    }

    private static Stream<Arguments> provideParametersValid() {
        return Stream.of(
                Arguments.of(1, "paulo@gmail.com"),
                Arguments.of(2, "paulo@gmail.com")
        );
    }
}
