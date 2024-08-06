package com.i2i.ems.employee.service;

import java.time.LocalDate;
import java.util.List;

import com.i2i.ems.model.Employee;
import com.i2i.ems.exceptions.EmployeeException;

/** 
 * Handles operations on employee such as adding, deleting, etc
 */
public interface EmployeeService {

    /** 
     * Adds an Employee to the repository
     * 
     * @param name          The name of the Employee
     * @param departmentId  The Id of the department
     * @param dateOfBirth   The DOB of the Employee
     * @param phoneNumber   The phone number of Employee
     * @param city          The city of the Employee
     */    
    public void addEmployee(String name, int departmentId,
                            LocalDate dateOfBirth, long phoneNumber,
                            String doorNumber, String locality, 
                            String city) throws EmployeeException;

    /** 
     * <p>
     * Gets a specific Employee from the available employees
     * <p>
     * 
     * @param id                  The ID of the Employee to be searched
     * @return                    The found employee, else null.
     * @throws EmployeeException  Happens when error occurs while adding
     *                            a specific employee detail.     
     */
    public Employee getEmployeeById(int id) throws EmployeeException;

    /** 
     * <p>
     * Updates an Employee with the new employee data
     * </p>
     * 
     * @param employee            The Employee containing updated data
     * @throws EmployeeException  Happens when error occurs while updating
     *                            a specific employee detail.
     */
    public void updateEmployee(Employee employee) throws EmployeeException;

    /** 
     * <p>
     * Soft deletes an Employee in the repository
     * </p>
     * 
     * @param id                  The Id of the Employee to be deleted
     * @throws EmployeeException  Happens when error occurs while deleting
     *                            a specific employee detail.
     */
    public void deleteEmployee(int id) throws EmployeeException;    

    /** 
     * <p>
     * Gets Employees considering isDeleted value
     * </p>
     * 
     * @return  The Employees data without soft deletion
     * @throws EmployeeException  Happens when error occurs while getting
     *                            employee details.
     */
    public List<Employee> getEmployees() throws EmployeeException;

    /** 
     * <p>
     * Gets Employees without considering isDeleted value
     * </p>
     * 
     * @return  The Employees data regardless of soft deletion
     * @throws EmployeeException  Happens when error occurs while getting
     *                            all employee details.
     */
    public List<Employee> getAllEmployees() throws EmployeeException;

}