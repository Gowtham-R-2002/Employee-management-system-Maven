package com.i2i.ems.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

/**
 * <p>
 * Address is the exact residing location of a person
 * Represents a employee's address and contains details of the address.
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "door_number")
    private String doorNumber;

    private String locality;
    private String city;

    public Address() {}

    public Address(String doorNumber, String locality, String city) {
        this.doorNumber = doorNumber;
        this.locality = locality;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoorNumber() {
        return doorNumber;
    }

    public void setDoorNumber(String doorNumber) {
        this.doorNumber = doorNumber;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    } 

    @Override
    public String toString() {
        return getDoorNumber() + " " + getLocality() + " " + getCity();
    }
}