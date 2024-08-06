package com.i2i.ems.employee.dao;

import java.util.List;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Employee;
 
/** 
 * Handles operations on employee such as creating, updating, etc.
 * @author   Gowtham R
 * @version  1.0
 */
public interface EmployeeDao {
    
    /**
     * <p>
     * Adds an Employee with ID assigned as auto-incremented value
     * </p>
     *
     * @param employee            The Employee to be added
     * @throws EmployeeException  Exception thrown when adding 
                                  employee has some issues.
     */
    public void createEmployee(Employee employee) throws EmployeeException;

    /**
     * <p>
     * Updates an Employee in the repository with the new Employee values
     * </p>
     *
     * @param employee            The Employee containing updated data
     * @throws EmployeeException  Exception thrown when updating an employee
     *                            has some issues.
     */
    public void updateEmployee(Employee employee) throws EmployeeException;

    /**
     * <p>
     * Deletes an Employee with changes in isDeleted field for soft deletion.
     * </p>
     * 
     * @param id                  The ID of the employee to be soft deleted.
     * @throws EmployeeException  Exception thrown when removing an employee has some issues.
     */
    public void removeEmployee(int id) throws EmployeeException;

    /**
     * <p>
     * Gets a specific employee from the given ID
     * </p>
     *
     * @param id  The ID of the Employee to be searched for
     * @return                    The found Employee else null
     * @throws EmployeeException  Exception thrown when fetching an employee 
     *                            with the given id has some issues.
     */
    public Employee fetchEmployeeById(int id) throws EmployeeException;

    /**
     * <p>
     * Gets all Employee considering isDeleted value
     * </p>
     *
     * @param <Employee>  The Entity Employee
     * @return            All Employee data without soft deletion.
     * @throw             Exception thrown when fetching all present 
     *                    employees has some issues.
     */
    public List<Employee> fetchEmployees() throws EmployeeException;

    /**
     * <p>
     * Gets all Employee without considering isDeleted value
     * </p>
     *
     * @param <Employee>         The Entity Employee
     * @return                   All Employee data on the repository
     * @throw EmployeeException  Exception thrown when fetching all employees 
                                 has some issues.
     */
    public List<Employee> fetchAllEmployees() throws EmployeeException;
}