package com.example.JwtTuto.service;

import com.example.JwtTuto.dto.MaterialDTO;
import com.example.JwtTuto.entity.Material;

import java.util.List;

public interface MaterialService {
    Material createMaterial(MaterialDTO materialDTO, int compartmentId);
    Material updateMaterial(MaterialDTO materialDTO, int compartmentId, int materialId);
    void deleteMaterial(int id);
    Material getMaterialById(int id);
    List<Material> getAllMaterials();
}
