package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PetService petService;

    public CustomerService(CustomerRepository customerRepository, PetService petService){
        this.petService = petService;
        this.customerRepository=customerRepository;
    }

    public Customer saveCustomer(Customer customer){

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerByPetID(Long petId){
        Pet pet = petService.getPetById(petId);
        Customer customer = pet.getCustomer();
        return customer;
    }

    public Customer getCustomerByID(Long customerId){
        Customer customer = new Customer();
        Optional<Customer> optionalCustomer =  customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()){
            customer = optionalCustomer.get();
        }
        return customer;
    }
}
