package com.example.JwtTuto.repository;

import com.example.JwtTuto.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Integer> {
    Optional<Material> findById(int id);
    List<Material> findAll();
    void deleteById(int id);
    Optional<Material> findByName(String name);
}
