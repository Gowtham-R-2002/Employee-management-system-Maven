package com.i2i.ems.model;

import java.util.Set;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.i2i.ems.model.Employee;

/** 
 * <p>
 * Certificate is a reward or proof for certain acheivement given for a person.
 * Represents a certificate given to an employee that contains name of the certificate 
 * and the collection of related employees in it 
 * </p>
 *
 * @author  Gowtham R
 * @version 1.0
 */

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue
    @Column(name = "cerificate_id")
    private int id;

    @Column(name = "certificate_name")
    private String name;
    
    @Column(name = "isDeleted")
    private boolean isDeleted;
 
    @ManyToMany(mappedBy = "certificates", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Certificate() {}

    public Certificate(String name) {
        this.name = name;
    }

    public Certificate(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Certificate that = (Certificate) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}