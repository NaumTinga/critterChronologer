package com.udacity.jdnd.course3.critter.entities;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Nationalized
    private String name;
    private String phoneNumber;
    private String notes;

    @OneToMany(mappedBy = "ownerId",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> petIds = new ArrayList<>();

    public Customer(long id, String name, String phoneNumber, String notes, List<Pet> petIds) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.notes = notes;
        this.petIds = petIds;
    }

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Pet> petIds) {
        this.petIds = petIds;
    }
}
