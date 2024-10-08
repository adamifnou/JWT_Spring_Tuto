package com.example.JwtTuto.service;

import com.example.JwtTuto.dto.CompartmentDTO;
import com.example.JwtTuto.entity.Compartment;

import java.util.List;
import java.util.Optional;

public interface CompartmentService {
    Compartment createCompartment(CompartmentDTO compartmentToAdd);
    Compartment updateCompartment(CompartmentDTO compartmentToUpdate, int id);
    void deleteCompartment(int id);
    List<Compartment> getAllCompartments();
    List<Compartment> getAvailableCompartments();
    Compartment getCompartmentById(int id);

    void updateCompartmentCurrentLoad(int id, int load);
    void addCompartmentCurrentLoad(int id, int load);
    void removeCompartmentCurrentLoad(int id, int load);
    int getCompartmentCurrentLoad(int id);
}
