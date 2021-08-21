package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.manutencao_predial.model.User;

public interface UserRepository extends JpaRepository<User, String>{
	@Query(value = "SELECT * FROM user r WHERE r.email = ?1 LIMIT 1", nativeQuery = true)
    public User login(String email);
}
