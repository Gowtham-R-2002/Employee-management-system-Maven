package com.i2i.ems.department.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

/** 
 * <p>
 * Handles operations on Department such as adding department
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public interface DepartmentService {

    /** 
     * <p> 
     * Adds a Department to the available departments
     * </p>
     * 
     * @param name  The name of the Department
     * @throws EmployeeException  Happens when error occurs while adding
     *                            a department.
     */
    public void addDepartment(String name) throws EmployeeException;

    /** 
     * <p> 
     * Gets a specific Department from available departments
     * </p>
     * 
     * @param id  The Id of the Department
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific department detail.
     */
    public Department getDepartment(int id) throws EmployeeException;

    /** 
     * <p>
     * Gets all available Departments
     * </p>
     * 
     * @param <Integer>     The Id of the Department
     * @param <Department>  The Department associated with the Id
     * @return              The Department data
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            all department detail.
     */
    public Map<Integer, Department> getDepartments() throws EmployeeException;

    /**
     * <p>
     * Updates a specific Department name
     * </p>
     * @param departmentId       The ID of the specific department to be updated
     * @param name                The updated department name
     * @throws EmployeeException  Happens when error occurs while updating
     *                            a specific department detail.
     */
    public void updateDepartment(int departmentId, String name) throws EmployeeException;

    /**
     * <p>
     * Deletes a specific Department
     * </p>
     * @param departmentId       The ID of the specific department to be deleted
     * @throws EmployeeException  Happens when error occurs while deleting
     *                            a specific department detail.
     */
    public void deleteDepartment(int departmentId) throws EmployeeException;

    /**
     * <p>
     * Fetches Employees from the specific department id provided
     * @param id  The Id of the specific department
     * @return    The Employees associated with the given department id
     * @throw EmployeeException  Exception thrown when fetching all employees of
                                 department has some issues.
     */
    public Set<Employee> getDepartmentEmployees(int id) throws EmployeeException ;

}