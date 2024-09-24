package com.example.JwtTuto.controller;

import com.example.JwtTuto.dto.AuthResponseDTO;
import com.example.JwtTuto.dto.RegisterResponseDTO;
import com.example.JwtTuto.dto.UserRegisterDTO;
import com.example.JwtTuto.entity.AuthRequest;
import com.example.JwtTuto.entity.UserInfo;
import com.example.JwtTuto.service.JwtService;
import com.example.JwtTuto.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for handling user authentication and management.
 */
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint to add a new user.
     *
     * @param userRegisterDto Data transfer object containing user registration details.
     * @return ResponseEntity with the result of the operation.
     */
    @PostMapping(value = "/addNewUser", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addNewUser(@RequestBody UserRegisterDTO userRegisterDto) {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(userRegisterDto.getEmail());
            userInfo.setName(userRegisterDto.getName());
            userInfo.setPassword(userRegisterDto.getPassword());
            userInfo.setRoles("ROLE_USER");
            service.addUser(userInfo);
            // return a json with message and code 200
            return ResponseEntity.ok(new RegisterResponseDTO("User " + userInfo.getName() + " Added Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RegisterResponseDTO(e.getMessage()));
        }
    }

    /**
     * Endpoint to add a new admin.
     *
     * @param userRegisterDto Data transfer object containing admin registration details.
     * @return ResponseEntity with the result of the operation.
     */
    @PostMapping("/addNewAdmin")
    public ResponseEntity<Object> addNewAdmin(@RequestBody UserRegisterDTO userRegisterDto) {
        try {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail(userRegisterDto.getEmail());
            userInfo.setName(userRegisterDto.getName());
            userInfo.setPassword(userRegisterDto.getPassword());
            userInfo.setRoles("ROLE_ADMIN");
            if (service.hasAdmin()) {
                if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                    return ResponseEntity.status(403).body("Only admins can create new admins");
                }
            }
            service.addUser(userInfo);
            return ResponseEntity.ok(new RegisterResponseDTO("Admin " + userInfo.getName() + " Added Successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new RegisterResponseDTO(e.getMessage()));
        }
    }

    /**
     * Endpoint to delete a user by ID.
     *
     * @param id The ID of the user to be deleted.
     * @return ResponseEntity with the result of the operation.
     */
    @DeleteMapping("/admin/deleteUser")
    public ResponseEntity<Object> deleteUser(@RequestParam(name = "id") int id) {
        try {
            service.deleteUser(id);
            return ResponseEntity.ok("User with id " + id + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Endpoint to update a user's profile.
     *
     * @param id The ID of the user to be updated.
     * @param userRegisterDto Data transfer object containing updated user details.
     * @return ResponseEntity with the result of the operation.
     */
    @PutMapping("/user/updateProfile")
    public ResponseEntity<Object> updateUserProfile(@RequestParam(name = "id") int id, @RequestBody UserRegisterDTO userRegisterDto) {
        try {
            if (!service.existsById(id)) {
                throw new IllegalArgumentException("User not found");
            } else {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(id);
                userInfo.setEmail(userRegisterDto.getEmail());
                userInfo.setName(userRegisterDto.getName());
                userInfo.setPassword(userRegisterDto.getPassword());
                userInfo.setRoles("ROLE_USER");
                service.updateUser(userInfo);
                return ResponseEntity.ok("User " + userInfo.getName() + " Updated Successfully");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint to authenticate a user and generate a JWT token.
     *
     * @param authRequest Data transfer object containing authentication details.
     * @return The generated JWT token.
     */
    @PostMapping("/generateToken")
    public ResponseEntity<Object> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try{
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            // return a json with jwt token and user id and name
            // find user by email
            UserInfo user = service.getUserByEmail(authRequest.getEmail());
            return ResponseEntity.ok(new AuthResponseDTO(jwtService.generateToken(authRequest.getEmail()), user.getId(), user.getName(), user.getEmail()));
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}