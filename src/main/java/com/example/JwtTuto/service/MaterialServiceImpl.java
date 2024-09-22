package com.example.JwtTuto.service;

import com.example.JwtTuto.constant.TransactionType;
import com.example.JwtTuto.dto.MaterialDTO;
import com.example.JwtTuto.entity.Compartment;
import com.example.JwtTuto.entity.Material;
import com.example.JwtTuto.entity.MaterialTransaction;
import com.example.JwtTuto.entity.UserInfo;
import com.example.JwtTuto.repository.MaterialRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MaterialServiceImpl implements MaterialService{

    private final MaterialRepository materialRepository;
    private final TransactionService transactionService;
    private final CompartmentService compartmentService;
    private final UserInfoService userInfoService;

    @Override
    public Material createMaterial(MaterialDTO materialToAdd, int compartmentId) {
    // check if compartiment exists
        // try to update the compartment current load
        // if it fails, throw an exception
        // if it succeeds, create the material
        // if the material creation fails, rollback the compartment current load update
        // if the material creation succeeds, return the material
        try {
            Compartment compartment = compartmentService.getCompartmentById(compartmentId);
            if (compartment != null){
                try {
                    compartmentService.addCompartmentCurrentLoad(compartmentId, materialToAdd.getQuantity());
                } catch (Exception e){
                    throw new RuntimeException("Error updating compartment current load: " + e.getMessage());
                }
                try{
                    // check if there is a material with the same name in the compartment
                    Material materialWithSameName = materialRepository.findByName(materialToAdd.getName()).orElse(null);
                    if (materialWithSameName != null){
                        throw new RuntimeException("Material with the same name already exists in the compartment");
                    }
                    Material material = new Material();
                    material.setName(materialToAdd.getName());
                    material.setQuantity(materialToAdd.getQuantity());
                    material.setCompartment(compartment);
                    return materialRepository.save(material);
                } catch (Exception e){
                    try {
                        compartmentService.removeCompartmentCurrentLoad(compartmentId, materialToAdd.getQuantity());
                    } catch (Exception ex){
                        throw new RuntimeException("Error rolling back compartment current load: " + ex.getMessage());
                    }
                    throw new RuntimeException("Error creating material: "+e.getMessage());
                }
            }else{
                throw new RuntimeException("Compartment not found");
            }
        } catch (Exception e){
            throw new RuntimeException("Error creating material " + e.getMessage());
        }
    }


    @Override
    public Material updateMaterial(MaterialDTO materialToUpdate, int compartmentId, int materialId) {
    // check if compartiment exists
        // try to update the compartment current load
        // if it fails, throw an exception
        // if it succeeds, update the material
        // if the material update fails, rollback the compartment current load update
        // if the material update succeeds, return the material
        try {
            Compartment compartment = compartmentService.getCompartmentById(compartmentId);
            if (compartment != null){
                try {
                    int delta = materialToUpdate.getQuantity() - getMaterialQuantity(materialId);
                    compartmentService.addCompartmentCurrentLoad(compartmentId, delta);
                } catch (Exception e){
                    throw new RuntimeException("Error updating compartment current load: "+e.getMessage());
                }
                try{
                    Material material = materialRepository.findById(materialId).orElse(null);
                    if (material != null){
                        material.setName(materialToUpdate.getName());
                        material.setQuantity(materialToUpdate.getQuantity());
                        material.setCompartment(compartment);
                        return materialRepository.save(material);
                    }else{
                        throw new RuntimeException("Material not found ");
                    }
                } catch (Exception e){
                    try {
                        int delta = getMaterialQuantity(materialId) - materialToUpdate.getQuantity();
                        compartmentService.removeCompartmentCurrentLoad(compartmentId, delta);
                    } catch (Exception ex){
                        throw new RuntimeException("Error rolling back compartment current load: " + ex.getMessage());
                    }
                    throw new RuntimeException("Error updating material: " + e.getMessage());
                }
            }else{
                throw new RuntimeException("Compartment not found: ");
            }
        } catch (Exception e){
            throw new RuntimeException("Error updating material: " + e.getMessage());
        }
    }

    @Override
    public void deleteMaterial(int id) {
        Material material = getMaterialById(id);
        if (material != null){
            try {
                Compartment compartment = material.getCompartment();
                if (compartment != null){
                    compartmentService.removeCompartmentCurrentLoad(compartment.getId(), material.getQuantity());
                    materialRepository.deleteById(id);
                }else{
                    throw new RuntimeException("Compartment not found");
                }
            } catch (Exception e){
                throw new RuntimeException("Error deleting material: " + e.getMessage());
            }
        }else{
            throw new RuntimeException("Material not found");
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

    @Override
    public int getMaterialQuantity(int id) {
        Material material = getMaterialById(id);
        if (material != null){
            return material.getQuantity();
        }else{
            throw new RuntimeException("Material not found");
        }
    }

    @Override
    public void updateMaterialQuantity(int materialId, int quantity) {
        Material material = getMaterialById(materialId);
        if (material != null){
            // check if quantity is not negative
            if (quantity < 0){
                throw new RuntimeException("Quantity cannot be negative");
            }
            Compartment compartment = material.getCompartment();
            if (compartment != null){
                material.setQuantity(quantity);
                materialRepository.save(material);

            }else{
                throw new RuntimeException("Compartment not found");
            }
        }else{
            throw new RuntimeException("Material not found");
        }
    }

    @Override
    public void addMaterialQuantity(int materialId, int quantity, int executorId) {
        Material material = getMaterialById(materialId);
        if (material != null){

            // add quantity to its compartment
            Compartment compartment = material.getCompartment();
            if (compartment != null) {
                compartmentService.addCompartmentCurrentLoad(compartment.getId(), quantity);

                updateMaterialQuantity(materialId, material.getQuantity() + quantity);

                MaterialTransaction transaction = new MaterialTransaction();
                transaction.setMaterial(material);
                transaction.setQuantity(quantity);
                transaction.setTransactionType(TransactionType.OUTBOUND);
                transaction.setDescription("Added " + quantity + " to material " + material.getName());
                transaction.setTransactionDate(java.time.LocalDateTime.now());
                UserInfo executor = userInfoService.getUserById(executorId);
                transaction.setExecutor(executor);

                transactionService.saveTransaction(transaction);

            }else{
                throw new RuntimeException("Compartment not found");
            }
        }else{
            throw new RuntimeException("Material not found");
        }
    }

    @Override
    public void removeMaterialQuantity(int materialId, int quantity, int executorId) {
        Material material = getMaterialById(materialId);
        if (material != null){
            if(material.getQuantity() - quantity < 0){
                throw new RuntimeException("Trying to remove more than available quantity");
            }
            Compartment compartment = material.getCompartment();
            if (compartment != null) {
                compartmentService.removeCompartmentCurrentLoad(compartment.getId(), quantity);
                updateMaterialQuantity(materialId, material.getQuantity() - quantity);

                MaterialTransaction transaction = new MaterialTransaction();
                transaction.setMaterial(material);
                transaction.setQuantity(quantity);
                transaction.setTransactionType(TransactionType.OUTBOUND);
                transaction.setDescription("Removed " + quantity + " from material " + material.getName());
                transaction.setTransactionDate(java.time.LocalDateTime.now());
                UserInfo executor = userInfoService.getUserById(executorId);
                transaction.setExecutor(executor);

                transactionService.saveTransaction(transaction);
            }else{
                throw new RuntimeException("Compartment not found");
            }
        }else{
            throw new RuntimeException("Material not found");
        }
    }

}
