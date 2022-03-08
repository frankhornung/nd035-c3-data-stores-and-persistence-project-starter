package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository=employeeRepository;
    }

    public void setAvailability(Long id, Set availability){
         Optional<Employee> optionalEmployee = employeeRepository.findById(id);
         if(optionalEmployee.isPresent()){
             Employee employee = optionalEmployee.get();
             employee.setDaysAvailable(availability);
             employeeRepository.save(employee);
         }
         else{
             // https://www.baeldung.com/hibernate-entitynotfoundexception
             throw new EntityNotFoundException();
         }

    }

    public Employee getEmployee(Long id){
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if(optionalEmployee.isPresent()){
            return optionalEmployee.get();
        }
        else{
            // https://www.baeldung.com/hibernate-entitynotfoundexception
            throw new EntityNotFoundException();
        }
    }

    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }
}
