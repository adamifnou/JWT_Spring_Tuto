package com.example.JwtTuto.service;

import com.example.JwtTuto.entity.UserInfo;
import com.example.JwtTuto.repository.UserInfoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(email); // Assuming 'email' is used as username

        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public void addUser(UserInfo userInfo) {
        // check if user already exists by email
        if (existsByEmail(userInfo.getEmail())) {
            throw new IllegalArgumentException("User already exists");
        }
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
    }
    public void updateUser(UserInfo userInfo) {
        //check if user exists
        if (!existsById(userInfo.getId())) {
            throw new IllegalArgumentException("User not found");
        }else{
            // check if email is already used by another user except the current user
            if (existsByEmail(userInfo.getEmail()) && !(repository.findByEmail(userInfo.getEmail()).get().getId() == userInfo.getId())) {
                throw new IllegalArgumentException("Email already used by another user");
            }
        }


        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
    }
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
    public Boolean existsById(int id) {
        return repository.existsById(id);
    }
    public boolean hasAdmin() {
        List<UserInfo> admins = repository.findByRolesContaining("ROLE_ADMIN");
        return !admins.isEmpty();
    }

    public void deleteUser(int id) {
        // check if user exists
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("User not found");
        }
        repository.deleteById(id);
    }
}

