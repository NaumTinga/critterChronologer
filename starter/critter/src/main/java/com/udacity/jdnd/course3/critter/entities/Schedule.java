package com.udacity.jdnd.course3.critter.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Schedule {


    @Id
    @GeneratedValue
    private long id;
//    private List<Long> employeeIds;
//    private List<Long> petIds;
    private LocalDate date;
}
