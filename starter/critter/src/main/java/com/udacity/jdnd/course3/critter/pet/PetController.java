package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    // DTO concept see Lesson 2 - 19
    private Pet petDTOToPojo(PetDTO petDTO){
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);

        Customer customer = customerService.getCustomerByID(petDTO.getOwnerId());
        List<Pet> customerPets = customer.getPets();
        if(petDTO.getOwnerId() >0){
            customerPets.add(pet);
            customerService.saveCustomer(customer);
        }

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

    // TODO
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    // TODO
    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    // TODO
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

}
