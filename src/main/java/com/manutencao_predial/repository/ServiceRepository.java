package com.manutencao_predial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.manutencao_predial.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer>{

    @Query(value = "SELECT * FROM service s "+
        "WHERE s.manager IS NULL", nativeQuery = true)
    public List<Service> findNotifications();

    @Query(value = "SELECT * FROM service s "+
        "WHERE s.manager = ?1", nativeQuery = true)
    public List<Service> findServices(String cpfGerente);

    @Query(value = "SELECT * FROM service s "+
        "ORDER BY s.state", nativeQuery = true)
    public List<Service> findAllOrderByState();
}
