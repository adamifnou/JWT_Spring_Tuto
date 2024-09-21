package com.example.JwtTuto.repository;

import com.example.JwtTuto.entity.Compartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompartmentRepository extends JpaRepository<Compartment, Integer> {
    Optional<Compartment> findById(int id);
    List<Compartment> findAllByIsAvailableTrue();
    void deleteById(int id);
}
