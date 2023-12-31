package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entities.Customer;
import com.udacity.jdnd.course3.critter.entities.Employee;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;


    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds){
        List<Pet> pets = petRepository.findAllById(petIds);
        List<Employee> employees = employeeRepository.findAllById(employeeIds);

        schedule.setPetIds(pets);
        schedule.setEmployeesIds(employees);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules(){
        return scheduleRepository.findAll();
    }

    public List<Schedule> getEmployeeSchedule(Long employeeId) {
        Employee employee = employeeRepository.getOne(employeeId);
        List<Schedule> schedules = scheduleRepository.findByEmployeesIds(employee);
        return schedules;
    }

    public List<Schedule> getCustomerSchedule(Long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        List<Schedule> schedules = scheduleRepository.findByPetIdsIn(customer.getPetIds());
        return schedules;
    }

    public List<Schedule> getPetSchedule(Long petId){
        Pet pet = petRepository.getOne(petId);
        List<Schedule> schedules = scheduleRepository.findByPetIds(pet);
        return schedules;
    }



}
