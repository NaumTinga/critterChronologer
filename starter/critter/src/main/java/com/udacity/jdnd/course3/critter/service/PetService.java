package com.udacity.jdnd.course3.critter.service;


import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    public PetService(CustomerRepository customerRepository, PetRepository petRepository) {
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }


    public Pet savePet(Pet pet, Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);

        // Add the new pet to the owner's list of pets
        pet.setOwnerId(customer);
        pet = petRepository.save(pet);
        customer.getPetIds().add(pet); // Add the pet to the existing list
        customerRepository.save(customer);

        return pet;
    }



    public Pet getPetById(Long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> getPetsByOwnerId(Long ownerId) {
        List<Pet> pets = petRepository.findPetByOwnerId(ownerId);
        return pets;
    }


}
