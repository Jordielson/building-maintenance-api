package com.manutencao_predial.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.manutencao_predial.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    public void findUserByEmailTest() {
        User user = new User("979.797.949-94", "Joao", "joao@gmail.com", "123456", "Gerente");
        userRepository.save(user);
        
        User userFound = userRepository.findUserByEmail("joao@gmail.com");

        assertEquals(userFound.getEmail(), "joao@gmail.com");
        assertEquals(userFound.getCpf(), "979.797.949-94");
        assertEquals(userFound.getName(), "Joao");
        assertEquals(userFound.getJob(), "Gerente");
    }

    @Test
    public void findProvidersTest() {
        User prestador = new User("979.797.949-95", "Pedro", "pedro@gmail.com", "123456", "Prestador");
        userRepository.save(prestador);

        User prestador2 = new User("154.554.646-24", "Paulo", "paulo@gmail.com", "123456", "Prestador");
        userRepository.save(prestador2);

        User gerente = new User("979.797.949-94", "Joao", "joao@gmail.com", "123456", "Gerente");
        userRepository.save(gerente);

        User chefe = new User("619.497.979-79", "Victor", "victor@gmail.com", "123456", "Chefe de Setor");
        userRepository.save(chefe);

        User administrador = new User("154.554.676-56", "jord", "jord@gmail.com", "123456", "Administrador");
        userRepository.save(administrador);

        List<User> providers = userRepository.findProviders();

        assertEquals(providers.size(), 2);
        assertTrue(providers.contains(prestador));
        assertTrue(providers.contains(prestador2));

        User providerFound = providers.stream().filter(item -> item.equals(prestador)).findFirst().orElse(null);
        
        assertEquals(providerFound.getCpf(), prestador.getCpf());
        assertEquals(providerFound.getEmail(), prestador.getEmail());
        assertEquals(providerFound.getJob(), prestador.getJob());
        assertEquals(providerFound.getName(), prestador.getName());
    }
}
