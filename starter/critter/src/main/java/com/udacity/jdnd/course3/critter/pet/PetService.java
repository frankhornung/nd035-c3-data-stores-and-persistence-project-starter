package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository){
        this.petRepository=petRepository;
    }

    public Pet savePet(Pet pet){
        //Pet newPet = new Pet();
        //newPet.setName("Fluffy");
        //newPet.setBirthDate(LocalDate.of(2011,1,11));
        return petRepository.save(pet);
    }

    public Pet getPetById(Long petId){
        Pet pet = new Pet();
        Optional<Pet> optionalPet = petRepository.findById(petId);
        if(optionalPet.isPresent()){
            pet = optionalPet.get();
        }
        return pet;
    }
}
