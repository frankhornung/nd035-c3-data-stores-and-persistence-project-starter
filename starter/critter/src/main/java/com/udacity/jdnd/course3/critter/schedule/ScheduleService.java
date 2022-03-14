package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final CustomerService customerService;

    public ScheduleService(ScheduleRepository scheduleRepository, CustomerService customerService){
        this.scheduleRepository=scheduleRepository;
        this.customerService=customerService;
    }

    public Schedule save(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getSchedulesForEmployee(Long employeeId){
        return scheduleRepository.findAllByEmployeesId(employeeId);
    }
    public List<Schedule> getSchedulesForPet(Long petId){
        return scheduleRepository.findAllByPetsId(petId);
    }

    public List<Schedule> getSchedulesForCustomer(Long customerId){

        Customer customer = customerService.getCustomerByID(customerId);
        List<Schedule> schedules = new ArrayList<>();
        for (Pet p : customer.getPets()){
            for(Schedule s : scheduleRepository.findAllByPetsId(p.getId())){
                schedules.add(s);
            }
        }
        return schedules;
    }


}
