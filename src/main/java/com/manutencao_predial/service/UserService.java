package com.manutencao_predial.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.manutencao_predial.model.User;
import com.manutencao_predial.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    public User findByEmail(String email) {
    	return userRepository.findByEmail(email).get(0);
    }
    public List<User> findProviders() { 
        return userRepository.findAll();
    }

    public Optional<User> findById(String cnpj) {
        Optional<User> userO = userRepository.findById(cnpj);
		return userO;
    }

    public User save(User user) {
 
    	int age = user.ageCalculate();
			
		if(age < 18) {
			throw new RuntimeException();
		}
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public User login(String email, String password) {
        User user = userRepository.login(email);
        if(user == null || !user.getPassword().equals(password)) {
            return null;
        } else {
            return user;
        }
    }
}
