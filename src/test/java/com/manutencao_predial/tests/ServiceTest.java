package com.manutencao_predial.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import com.manutencao_predial.exceptions.NotFoundException;
import com.manutencao_predial.model.Service;
import com.manutencao_predial.service.ServiceService;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTest {
    @Autowired
	ServiceService serviceService;

    @ParameterizedTest(name = "Servico com id {0} esta sendo verificado")
    @ValueSource(ints = {-1, 0, 10, 100, 999})
	public void checkServiceNotFoundException(int idService) {
		assertThrows(NotFoundException.class, () -> serviceService.addProvider(idService, "paulo@gmail.com"));
	}

    @ParameterizedTest(name = "Prestador com email {0} esta sendo verificado")
    @ValueSource(strings = {"jose@gmail.com", "aaaa", "", "/n", " "})
    public void checkProviderNotFoundException(String email) {
        assertThrows(RuntimeException.class, () -> serviceService.addProvider(1, email));
    }

    @ParameterizedTest(name = "Servico com id {0} e Prestador com email {1} esta sendo verificado")
    @MethodSource("provideParametersNotFound")
    public void checkNotFoundException(int idService, String email) {
        assertThrows(NotFoundException.class, () -> serviceService.addProvider(idService, email));
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
        Service service = serviceService.addProvider(idService, email);
        assertTrue(service.getProviders().stream().anyMatch(item -> email.equals(item.getEmail())));
    }

    private static Stream<Arguments> provideParametersValid() {
        return Stream.of(
                Arguments.of(1, "paulo@gmail.com"),
                Arguments.of(2, "paulo@gmail.com")
        );
    }
}
