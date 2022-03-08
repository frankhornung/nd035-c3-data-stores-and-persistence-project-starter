package com.udacity.jdnd.course3.critter.user;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.DayOfWeek;
import java.util.Set;

@Entity
public class Employee extends User{

    // Lesson 2 - 11 Entity relationships
    //  https://classroom.udacity.com/nanodegrees/nd035/parts/cd0628/modules/c9bb7e7f-f34d-466a-a3f2-d7d191b261dc/lessons/fc18b0dc-9b9f-4177-a78d-eb3eef7d1047/concepts/668d6803-2782-4555-b4d9-fc93ce4364c1
    @ElementCollection
    // https://tomee.apache.org/examples-trunk/jpa-enumerated/
    // https://www.baeldung.com/jpa-persisting-enums-in-jpa
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public Employee(){

    }

    public Employee(Set<EmployeeSkill> skills){
        this.skills=skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }
}
