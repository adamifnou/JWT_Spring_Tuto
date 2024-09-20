package com.example.JwtTuto.repository;

import com.example.JwtTuto.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByEmail(String email); // Use 'email' if that is the correct field for login
    void deleteById(int id);
    List<UserInfo>  findByRolesContaining(String role);
    Optional<UserInfo> findById(int id);
    Boolean existsByEmail(String email);
    Boolean existsById(int id);
}
