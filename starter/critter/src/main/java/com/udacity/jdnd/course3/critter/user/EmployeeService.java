package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

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

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skillSet){
        // https://www.baeldung.com/java-get-day-of-week
        DayOfWeek day = date.getDayOfWeek();
        System.out.println(day);
        String dayName = day.toString();
        //List<Employee> allEmployees = employeeRepository.findByDaysAvailableContains(day);
        //List<Employee> allEmployees = employeeRepository.findBySkillsIn(skillSet);
        List<Employee> allEmployees = employeeRepository.findBySkillsInAndDaysAvailableContains(skillSet,day);

        //List<Employee> allEmployees = employeeRepository.findBySkillsContains(skillSet);
        //List<Employee> allEmployees = employeeRepository.findAll();

        List<Employee> skilledAndAvailableEmployees = new ArrayList<Employee>();
        // https://stackoverflow.com/questions/32348453/python-counter-alternative-for-java
        final Map<Long, Integer> counter = new HashMap<>();
        for( Employee e : allEmployees){
            System.out.println(e.getName());
            counter.merge(e.getId(),1, Integer::sum);
        }
        System.out.println("xxxxxxxxxxxxx");
        for(  Long id : counter.keySet()){
            System.out.println(id);
            System.out.println(counter.get(id));
            if(counter.get(id) >= skillSet.size()){
                Optional<Employee> optionalEmployee = employeeRepository.findById(id);
                if (optionalEmployee.isPresent()){
                    skilledAndAvailableEmployees.add(optionalEmployee.get());
                }
            }
        }
        return skilledAndAvailableEmployees;
    }

}
