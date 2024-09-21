package com.example.JwtTuto.service;

import com.example.JwtTuto.dto.MaterialDTO;
import com.example.JwtTuto.entity.Compartment;
import com.example.JwtTuto.entity.Material;
import com.example.JwtTuto.repository.MaterialRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService{

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private CompartmentService compartmentService;

    @Override
    public Material createMaterial(MaterialDTO materialToAdd, int compartmentId) {
        Material material = new Material();
        Compartment compartment = compartmentService.getCompartmentById(compartmentId);
        if (materialToAdd != null){
            makeMaterial(materialToAdd, material, compartment);
        }
        return materialRepository.save(material);
    }

    private void makeMaterial(MaterialDTO materialToAdd, Material material, Compartment compartment) {
        material.setName(materialToAdd.getName());
        material.setCode(materialToAdd.getCode());
        material.setDescription(materialToAdd.getDescription());
        material.setQuantity(materialToAdd.getQuantity());
        if(compartment != null){
            material.setCompartment(compartment);
        }else {
            throw new RuntimeException("Compartment not found");
        }
    }

    @Override
    public Material updateMaterial(MaterialDTO materialToUpdate, int compartmentId, int materialId) {
        Material material = getMaterialById(materialId);
        Compartment compartment = compartmentService.getCompartmentById(compartmentId);
        if (material != null){
            makeMaterial(materialToUpdate, material, compartment);
        }else{
            throw new RuntimeException("Material not found");
        }
        return materialRepository.save(material);
    }

    @Override
    public void deleteMaterial(int id) {
        try {
            materialRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Error deleting material");
        }
    }

    @Override
    public Material getMaterialById(int id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public List<Material> getAllMaterials() {
        try {
            return materialRepository.findAll();
        } catch (Exception e){
            throw new RuntimeException("Error fetching materials");
        }
    }
}
