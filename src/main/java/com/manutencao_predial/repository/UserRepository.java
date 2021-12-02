package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.manutencao_predial.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	@Query(value = "SELECT * FROM user r WHERE r.email = ?1 LIMIT 1", nativeQuery = true)
    public User findUserByEmail(String email);

    @Query(value = "SELECT * FROM user r WHERE r.job = 'Prestador'", nativeQuery = true)
    public List<User> findProviders();
    
    public List<User> findByEmail(String email);
}
