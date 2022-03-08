package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDaysAvailableContains(DayOfWeek day);
    List<Employee> findBySkillsIn(Set skills);
    //List<Employee> findBySkillsContains(Set skills);

    // this returns a list of employees that are available on that day of week AND are having at least one of the required skills
    // This requires further checking in the service layer if the employee really has all of the required skills.
    List<Employee> findBySkillsInAndDaysAvailableContains(Set<EmployeeSkill> skills, DayOfWeek day);
}
