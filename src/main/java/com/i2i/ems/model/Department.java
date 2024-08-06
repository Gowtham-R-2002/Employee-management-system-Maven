package com.i2i.ems.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.i2i.ems.model.Employee;

/**
 * <p>
 * Represents a department within an organisation.
 * It contains information about the department such as
 * name, and employees associated with it.
 * </p>
 * @author  Gowtham R
 * @version 1.0
 */
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private int id;

    @Column(name = "department_name")
    private String name;

    @Column(name = "unique_id")
    private String uniqueId;

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Department() {}

    public Department(String name) {
        this.name = name;
        uniqueId = "";
        employees = new HashSet<>();
    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        employees = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}