package com.example.JwtTuto.controller;


import com.example.JwtTuto.entity.AuthRequest;
import com.example.JwtTuto.entity.UserInfo;
import com.example.JwtTuto.service.JwtService;
import com.example.JwtTuto.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public String deleteUser(
            @RequestParam(name = "id") int id
    ){
        service.deleteUser(id);
        return "User Deleted Successfully";
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            System.out.println("is authenticated");
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            System.out.println("is not authenticated");
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
