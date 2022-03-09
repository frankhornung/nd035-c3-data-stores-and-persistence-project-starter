package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import net.minidev.json.writer.BeansMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final CustomerService customerService;
    private final PetService petService;

    //@Autowired
    public UserController(UserService userService, EmployeeService employeeService, CustomerService customerService, PetService petService){
        this.customerService=customerService;
        this.userService=userService;
        this.employeeService=employeeService;
        this.petService=petService;
    }

    private Employee employeeDTOtoPojo(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        return employee;
    }

    private EmployeeDTO employeePojoToDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee,employeeDTO);
        return employeeDTO;
    }

    private List<EmployeeDTO> availableEmployeesToDTO(List<Employee> employeeList){
        List<EmployeeDTO> employeeDTOList = new ArrayList<EmployeeDTO>();
        for( Employee e : employeeList){
            employeeDTOList.add(employeePojoToDTO(e));
        }
        return employeeDTOList;
    }

    private Customer customerDTOtoPojo(CustomerDTO customerDTO){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        // TODO add code for populating the petIDs list in the DTO
        List<Pet> pets = new ArrayList<Pet>();
        if(customerDTO.getPetIds() != null){
            List<Long> petIds = customerDTO.getPetIds();
            for(Long petId : petIds){
                pets.add(petService.getPetById(petId));
            }
        }

        customer.setPets(pets);
        return customer;
    }

    private CustomerDTO customerPojoToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        List<Long> petIds = new ArrayList<Long>();
        List<Pet> pets = customer.getPets();
        if(pets != null){
            for(Pet p : pets){
                petIds.add(p.getId());
            }
            customerDTO.setPetIds(petIds);
        }

        return customerDTO;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        Customer newCustomer = customerService.saveCustomer(customerDTOtoPojo(customerDTO));
        return customerPojoToDTO(newCustomer);
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customersDTO = new ArrayList<CustomerDTO>();
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer c : customers){
            customersDTO.add(customerPojoToDTO(c));
        }
        return customersDTO;
        //throw new UnsupportedOperationException();
    }
    // TODO
    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        Customer customer = customerService.getCustomerByPetID(petId);
        return customerPojoToDTO(customer);
        //throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOtoPojo(employeeDTO);
        EmployeeDTO returnDTO = employeePojoToDTO(employeeService.saveEmployee(employee));
        return returnDTO;
        //throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        Employee employee = employeeService.getEmployee(employeeId);
        return employeePojoToDTO(employee);
        //throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setAvailability(employeeId, daysAvailable);
        //throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        return availableEmployeesToDTO(employeeService.findEmployeesForService(employeeDTO.getDate(),employeeDTO.getSkills()));
        //throw new UnsupportedOperationException();
    }

}
