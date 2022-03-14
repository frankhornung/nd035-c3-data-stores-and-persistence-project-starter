package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;




/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final EmployeeService employeeService;
    private final PetService petService;
    private final ScheduleService scheduleService;

    public ScheduleController(EmployeeService employeeService,  PetService petService, ScheduleService scheduleService){
        this.employeeService=employeeService;
        this.petService=petService;
        this.scheduleService=scheduleService;
    }

    // create a schedule for some pets that get taken care by a team of employees with specific skills on a specific date.
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return schedulePojoToDTO(scheduleService.save(scheduleDTOToPojo(scheduleDTO)));
    }

    // return all schedules that exist
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
        for(Schedule s : scheduleService.getAllSchedules()){
            scheduleDTOs.add(schedulePojoToDTO(s));
        }
        return scheduleDTOs;
    }

    // a pet can have multiple schedules when it is taken care by an employee
    // this method is to return the list of schedules for a specific pet.
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
        for (Schedule s : scheduleService.getSchedulesForPet(petId)){
            scheduleDTOs.add(schedulePojoToDTO(s));
        }
        return scheduleDTOs;
    }

    // an employee can have multiple schedules for taking care of multiple pets
    // this method is to return the list of schedules for a specific employee
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
        for (Schedule s : scheduleService.getSchedulesForEmployee(employeeId)){
            scheduleDTOs.add(schedulePojoToDTO(s));
        }
        return scheduleDTOs;
    }

    // a customer may have booked several petting schedules for his pets
    // return all the schedules booked by a specific customer
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<ScheduleDTO>();
        for (Schedule s : scheduleService.getSchedulesForCustomer(customerId)){
            scheduleDTOs.add(schedulePojoToDTO(s));
        }
        return scheduleDTOs;
    }

    // DTO for frontend(api) usage needs to be converted for internally used object representation
    public Schedule scheduleDTOToPojo(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(dto,schedule);

        List<Employee> employeeList = new ArrayList<Employee>();
        for (Long employeeId : dto.getEmployeeIds()){
            employeeList.add(employeeService.getEmployee(employeeId));
        }
        schedule.setEmployees(employeeList);

        List<Pet> petList = new ArrayList<Pet>();
        for (Long petId : dto.getPetIds()){
            petList.add(petService.getPetById(petId));
        }
        schedule.setPets(petList);

        return schedule;
    }

    // intenally used object needs to be converted to externally visible DTO structure
    public ScheduleDTO schedulePojoToDTO(Schedule schedule){
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        List<Long> employeeIds = new ArrayList<Long>();
        for(Employee e : schedule.getEmployees()){
            employeeIds.add(e.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        List<Long> petIds = new ArrayList<Long>();
        for(Pet p : schedule.getPets()){
            petIds.add(p.getId());
        }
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }
}
