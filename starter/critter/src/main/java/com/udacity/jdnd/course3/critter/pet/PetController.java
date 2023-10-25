package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    PetService petService;
    CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes());
        PetDTO convertedPet;
        try {
            convertedPet = convertPetToPetDTO(petService.savePet(pet, petDTO.getOwnerId()));
        } catch (Exception exception) {
            // Handle all types of exceptions and return informative error messages
            String errorMessage = "An error occurred while saving the pet.";
            if (exception.getMessage() != null) {
                errorMessage += " Details: " + exception.getMessage();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errorMessage, exception);
        }
        return convertedPet;
    }



    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable Long petId) {
        Pet pet;
        try {
            pet = petService.getPetById(petId);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet with id: " + petId + " not found", exception);
        }
        return convertPetToPetDTO(pet);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        return pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable Long ownerId) {

        List<Pet> pets;
        try {
            pets = petService.getPetsByOwnerId(ownerId);
        } catch (Exception exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Owner pet with id " + ownerId + " not found", exception);
        }
        return pets.stream().map(this::convertPetToPetDTO).collect(Collectors.toList());
    }



    private Pet convertPetDTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setType(petDTO.getType());
        pet.setName(petDTO.getName());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());

        if(petDTO.getOwnerId()!=0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setOwnerId(customer);
        }
        return pet;
    }

    private PetDTO convertPetToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setType(pet.getType());
        petDTO.setName(pet.getName());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setNotes(pet.getNotes());

        if (pet.getOwnerId() != null) {
            petDTO.setOwnerId(pet.getOwnerId().getId());
        } else {
            petDTO.setOwnerId(null);  // Set to null when there is no owner
        }

        // Set the id to the pet's id
        petDTO.setId(pet.getId());

        return petDTO;
    }


}
