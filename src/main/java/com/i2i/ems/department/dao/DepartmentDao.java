package com.i2i.ems.department.dao;

import java.util.Map;
import java.util.Set;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

public interface DepartmentDao {
    /**
     * <p>
     * Adds an Department to available departments with ID assigned as 
     * auto-incremented value.
     * </p>
     *
     * @param name               The name of the Department to be created.
     * @throw EmployeeException  Exception thrown when adding Department 
     *                           has some issues.
     */
    public void addDepartment(String name) throws EmployeeException;   
    
    /**
     * <p>
     * Gets a specific Department from the available departments 
     * </p>
     *
     * @param id                 The ID of the Certificate to be found
     * @return                   The found Certificate
     * @throw EmployeeException  Exception thrown when adding fetching a 
                                 Department has some issues.
     */    
    public Department getDepartment(int id) throws EmployeeException;

    /**
     * <p>
     * Gets all Departments available.
     * </p>
     *
     * @param <Integer>          The ID of the certificate
     * @param <Department>       The Department associated with the ID
     * @return                   The collection of all available Department
     * @throw EmployeeException  Exception thrown when fetching all department
                                 has some issues.
     */
    public Map<Integer, Department> getDepartments() throws EmployeeException;

    /**
     * <p>
     * Updates a specific Department name
     * </p>
     * @param certificateId       The ID of the specific certificate to be updated
     * @param name                The updated certificate name
     * @throws EmployeeException  Happens when error occurs while updating
     *                            a specific certificate detail.
     */
    public void updateDepartment(int certificateId, String name) throws EmployeeException;

    /**
     * <p>
     * Deletes a specific Department name
     * </p>
     * @param certificateId       The ID of the specific certificate to be deleted
     * @throws EmployeeException  Happens when error occurs while deleting
     *                            a specific certificate detail.
     */
    public void deleteDepartment(int certificateId) throws EmployeeException;

    /**
     * <p>
     * Fetches Employees from the specific department id provided
     * @param id  The Id of the specific department
     * @return    The Employees associated with the given department id
     * @throw EmployeeException  Exception thrown when fetching all employees of
                                 department has some issues.
     */
    public Set<Employee> getDepartmentEmployees(int id) throws EmployeeException;

}