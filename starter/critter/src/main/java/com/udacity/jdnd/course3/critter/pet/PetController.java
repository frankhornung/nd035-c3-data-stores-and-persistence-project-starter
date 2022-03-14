package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    // DTO concept see Lesson 2 - 19
    // https://knowledge.udacity.com/questions/812602
    private Pet petDTOToPojo(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        Customer customer = customerService.getCustomerByID(petDTO.getOwnerId());
        pet.setCustomer(customer);
        return pet;
    }


    private PetDTO petPojoToDTO(Pet pet){
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if(pet.getCustomer() != null){
            Customer customer = pet.getCustomer();
            petDTO.setOwnerId(customer.getId());
        }
        return petDTO;
    }

    private final PetService petService;
    private final CustomerService customerService;

    @Autowired
    public PetController(PetService petService, CustomerService customerService){

        this.petService=petService;
        this.customerService=customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet newPet = petService.savePet(petDTOToPojo(petDTO));
        return petPojoToDTO(newPet);
        //throw new UnsupportedOperationException();
    }

    // x1 newly implemented
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return petPojoToDTO(petService.getPetById(petId));
        //throw new UnsupportedOperationException();
    }

    // TODO
    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    // x1 newly implemented
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        Customer customer = customerService.getCustomerByID(ownerId);
        List<Pet> pets = customer.getPets();
        List<PetDTO> petDTOList = new ArrayList<PetDTO>();
        for ( Pet p : pets ){
            petDTOList.add(petPojoToDTO(p));
        }
        return petDTOList;
        //throw new UnsupportedOperationException();
    }

}
