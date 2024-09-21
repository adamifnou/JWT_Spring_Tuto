package com.example.JwtTuto.service;

import com.example.JwtTuto.dto.CompartmentDTO;
import com.example.JwtTuto.entity.Compartment;
import com.example.JwtTuto.repository.CompartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompartmentServiceImpl implements CompartmentService{

    private final CompartmentRepository compartmentRepository;

    public CompartmentServiceImpl(CompartmentRepository compartmentRepository){
        this.compartmentRepository = compartmentRepository;
    }

    @Override
    public Compartment createCompartment(CompartmentDTO compartmentToAdd){
        Compartment compartment = new Compartment();
        if (compartmentToAdd != null){
            compartment.setName(compartmentToAdd.getName());
            compartment.setCompartmentCode(compartmentToAdd.getCompartmentCode());
            compartment.setCompartimentType(compartmentToAdd.getCompartimentType());
            compartment.setDescription(compartmentToAdd.getDescription());
            compartment.setIsAvailable(true);
            compartment.setCapacity(compartmentToAdd.getCapacity());
            compartment.setCurrentLoad(0);
        }
        return compartmentRepository.save(compartment);
    }

    @Override
    public Compartment updateCompartment(CompartmentDTO compartmentToUpdate, int compartmentId){
        Compartment compartment = compartmentRepository.findById(compartmentId).orElse(null);
        if (compartment != null){
            compartment.setName(compartmentToUpdate.getName());
            compartment.setCompartmentCode(compartmentToUpdate.getCompartmentCode());
            compartment.setCompartimentType(compartmentToUpdate.getCompartimentType());
            compartment.setDescription(compartmentToUpdate.getDescription());
            compartment.setIsAvailable(true); // TODO: create availability service and use it here
            compartment.setCapacity(compartmentToUpdate.getCapacity());
            compartment.setCurrentLoad(0);// TODO: create compartimentLoadService and use it here: compartimentLoadService.getCurrentLoad(compartmentId)
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


}
