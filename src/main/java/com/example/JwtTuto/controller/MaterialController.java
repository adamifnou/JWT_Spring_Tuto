package com.example.JwtTuto.controller;

import com.example.JwtTuto.dto.MaterialDTO;
import com.example.JwtTuto.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The MaterialController class is a controller class that handles HTTP requests related to the material entity and its transactions.
 * It is used to create a REST API that can be used to perform CRUD operations on the material entity.
 * This controller is available at the endpoint "/api/material", only accessible by users with the role "ROLE_ADMIN".
 */
@AllArgsConstructor
@RestController
@RequestMapping("/userAPI/material")
public class MaterialController {

    private MaterialService materialService;

    //TODO: Implement the methods for the MaterialController class

    @GetMapping("/all")
    ResponseEntity<Object> getAllMaterials() {
        try {
            return ResponseEntity.ok(materialService.getAllMaterials());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getMaterial")
    ResponseEntity<Object> getMaterialById(@RequestParam int id) {
        try {
            return ResponseEntity.ok(materialService.getMaterialById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/add")
    ResponseEntity<Object> addMaterial(@RequestBody MaterialDTO materialDTO, @RequestParam int compartmentId) {
        try {
            return ResponseEntity.ok(materialService.createMaterial(materialDTO, compartmentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update")
    ResponseEntity<Object> updateMaterial(@RequestBody MaterialDTO materialDTO, @RequestParam int compartmentId, @RequestParam int materialId) {
        try {
            return ResponseEntity.ok(materialService.updateMaterial(materialDTO, compartmentId, materialId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    ResponseEntity<Object> deleteMaterial(@RequestParam int id) {
        try {
            materialService.deleteMaterial(id);
            return ResponseEntity.ok("Material deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addToMaterial")
    ResponseEntity<Object> addToMaterial(@RequestParam int materialId,
                                         @RequestParam int quantity,
                                         @RequestParam int executorId) {
        try {
            materialService.addMaterialQuantity(materialId, quantity, executorId);
            return ResponseEntity.ok("Material quantity updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/removeFromMaterial")
    ResponseEntity<Object> removeFromMaterial(@RequestParam int materialId,
                                              @RequestParam int quantity,
                                              @RequestParam int executorId) {
        try {
            materialService.removeMaterialQuantity(materialId, quantity, executorId);
            return ResponseEntity.ok("Material quantity updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}