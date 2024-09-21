package com.example.JwtTuto.controller;

import com.example.JwtTuto.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The MaterialController class is a controller class that handles HTTP requests related to the material entity and its transactions.
 * It is used to create a REST API that can be used to perform CRUD operations on the material entity.
 * This controller is available at the endpoint "/api/material", only accessible by users with the role "ROLE_ADMIN".
 */
@RestController
@RequestMapping("/userAPI/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

        //TODO: Implement the methods for the MaterialController class
}
