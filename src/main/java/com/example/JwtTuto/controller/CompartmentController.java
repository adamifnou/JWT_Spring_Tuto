package com.example.JwtTuto.controller;

import com.example.JwtTuto.dto.CompartmentDTO;
import com.example.JwtTuto.service.CompartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The CompartmentController class is a controller class that handles HTTP requests related to the compartment entity.
 * It is used to create a REST API that can be used to perform CRUD operations on the compartment entity.
 * This controller is available at the endpoint "/adminAPI/compartment", only accessible by users with the role "ROLE_ADMIN".
 */

@AllArgsConstructor
@RestController
@RequestMapping("/adminAPI/compartment")
public class CompartmentController
{

    private final CompartmentService compartmentService;

    @GetMapping("/all")
    ResponseEntity<Object> getAllCompartments()
    {
        try {
            return ResponseEntity.ok(compartmentService.getAllCompartments());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    ResponseEntity<Object> addCompartment(@RequestBody CompartmentDTO compartmentDTO)
    {
        try {
            return ResponseEntity.ok(compartmentService.createCompartment(compartmentDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    ResponseEntity<Object> updateCompartment(@RequestBody CompartmentDTO compartmentDTO,
                                             @RequestParam int id)
    {
        try {
            return ResponseEntity.ok(compartmentService.updateCompartment(compartmentDTO, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<Object> deleteCompartment(@RequestParam int id)
    {
        try {
            compartmentService.deleteCompartment(id);
            return ResponseEntity.ok("Compartment deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/available")
    ResponseEntity<Object> getAvailableCompartments()
    {
        try {
            return ResponseEntity.ok(compartmentService.getAvailableCompartments());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
