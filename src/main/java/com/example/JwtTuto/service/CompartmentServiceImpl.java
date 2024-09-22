package com.example.JwtTuto.service;

import com.example.JwtTuto.dto.CompartmentDTO;
import com.example.JwtTuto.entity.Compartment;
import com.example.JwtTuto.repository.CompartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CompartmentServiceImpl implements CompartmentService{

    private final CompartmentRepository compartmentRepository;

    @Override
    public Compartment createCompartment(CompartmentDTO compartmentToAdd) {
        try {
            Compartment compartment = new Compartment();
            if (compartmentToAdd != null) {
                // check if there is a compartment with the same name
                if (compartmentRepository.findByName(compartmentToAdd.getName()).isPresent()) {
                    throw new RuntimeException("Compartment with the same name already exists");
                }
                compartment.setName(compartmentToAdd.getName());
                compartment.setCompartmentCode(compartmentToAdd.getCompartmentCode());
                compartment.setCompartimentType(compartmentToAdd.getCompartimentType());
                compartment.setDescription(compartmentToAdd.getDescription());
                compartment.setIsAvailable(true);
                compartment.setCapacity(compartmentToAdd.getCapacity());
                compartment.setCurrentLoad(0);
            }
            return compartmentRepository.save(compartment);
        } catch (Exception e) {
            throw new RuntimeException("Error creating compartment: " + e.getMessage());
        }
    }

    @Override
    public Compartment updateCompartment(CompartmentDTO compartmentToUpdate, int compartmentId){
        Compartment compartment = compartmentRepository.findById(compartmentId).orElse(null);
        if (compartment != null){
            compartment.setName(compartmentToUpdate.getName());
            compartment.setCompartmentCode(compartmentToUpdate.getCompartmentCode());
            compartment.setCompartimentType(compartmentToUpdate.getCompartimentType());
            compartment.setDescription(compartmentToUpdate.getDescription());
            compartment.setIsAvailable(compartment.getIsAvailable()); // TODO: create availability service and use it here
            compartment.setCapacity(compartmentToUpdate.getCapacity());
            compartment.setCurrentLoad(compartment.getCurrentLoad());// TODO: create compartimentLoadService and use it here: compartimentLoadService.getCurrentLoad(compartmentId)
        }else{
            throw new RuntimeException("Compartment not found");
        }
        return compartmentRepository.save(compartment);
    }

    @Override
    public void deleteCompartment(int id){
        try {
            compartmentRepository.deleteById(id);
        } catch (Exception e){
            throw new RuntimeException("Error deleting compartment");
        }
    }

    @Override
    public List<Compartment> getAllCompartments(){
        try {
            return compartmentRepository.findAll();
        } catch (Exception e){
            throw new RuntimeException("Error fetching compartments");
        }
    }

    @Override
    public List<Compartment> getAvailableCompartments(){
        try {
            return compartmentRepository.findAllByIsAvailableTrue();
        } catch (Exception e){
            throw new RuntimeException("Error fetching compartments");
        }
    }

    @Override
    public Compartment getCompartmentById(int id){
        return compartmentRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompartmentCurrentLoad(int id, int load) {
    Compartment compartment = getCompartmentById(id);
        if (compartment != null) {
            if (load < 0) {
                throw new RuntimeException("Value of added material cannot be negative");
            }
            if (load > compartment.getCapacity()) {
                throw new RuntimeException("Load cannot be more than the capacity");
            }
            compartment.setCurrentLoad(load);
            compartmentRepository.save(compartment);
        }else{
            throw new RuntimeException("Compartment not found");
        }
    }

    @Override
    public void addCompartmentCurrentLoad(int id, int load) {
        // this function will add load to the current load of a compartment
        // get the compartment
        Compartment compartment = getCompartmentById(id);
        // check if compartment exists
        if (compartment != null) {
            // check if the load is not negative
            if (load < 0) {
                throw new RuntimeException("Value of added material cannot be negative");
            }
            // check if the load is not more than the capacity
            if (compartment.getCurrentLoad() + load > compartment.getCapacity()) {
                throw new RuntimeException("Load cannot be more than the capacity");
            }
            // update the current load
            compartment.setCurrentLoad(compartment.getCurrentLoad() + load);
            compartmentRepository.save(compartment);
        }else{
            throw new RuntimeException("Compartment not found");
        }
    }

    @Override
    public void removeCompartmentCurrentLoad(int id, int load) {
        // this function will remove load from the current load of a compartment
        // get the compartment
        Compartment compartment = getCompartmentById(id);
        // check if compartment exists
        if (compartment != null) {
            // check if the load is not negative
            if (load < 0) {
                throw new RuntimeException("Value of removed material cannot be negative");
            }
            // check if the load is not more than the current load
            if (compartment.getCurrentLoad() - load < 0) {
                throw new RuntimeException("Trying to remove more than available load");
            }
            // update the current load
            compartment.setCurrentLoad(compartment.getCurrentLoad() - load);
            compartmentRepository.save(compartment);
        }else{
            throw new RuntimeException("Compartment not found");
        }
    }

    @Override
    public int getCompartmentCurrentLoad(int id) {
        // this function will return the current load of a compartment
        // get the compartment
        Compartment compartment = getCompartmentById(id);
        // check if compartment exists
        if (compartment != null) {
            return compartment.getCurrentLoad();
        }else{
            throw new RuntimeException("Compartment not found");
        }
    }

}
