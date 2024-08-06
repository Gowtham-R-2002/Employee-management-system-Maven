package com.i2i.ems.employee.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.i2i.ems.department.service.DepartmentService;
import com.i2i.ems.department.service.DepartmentServiceImpl;
import com.i2i.ems.employee.dao.EmployeeDao;
import com.i2i.ems.employee.dao.EmployeeDaoImpl;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

public class EmployeeServiceImpl implements EmployeeService {
    DepartmentService departmentService = new DepartmentServiceImpl();
    EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void addEmployee(String name, int departmentId,
                            LocalDate dateOfBirth, long phoneNumber,
                            String doorNumber, String locality,
                            String city) throws EmployeeException {
        Department department = departmentService.getDepartment(departmentId);
        try {
            Address address = new Address(doorNumber, locality, city);
            Employee employee = Employee.Builder.newInstance()
                                                .setName(name)
                                                .setDepartment(department)
                                                .setDateOfBirth(dateOfBirth)
                                                .setPhoneNumber(phoneNumber)
                                                .setAddress(address)
                                                .build(); 
            employeeDao.createEmployee(employee);   
        } catch (EmployeeException e) {
             throw new EmployeeException("Error while creating employee with name \n" + name, e);           
        }
    }

    @Override
    public Employee getEmployeeById(int id) throws EmployeeException {
        if (isEmployeePresent(id)) {
            return employeeDao.fetchEmployeeById(id);
        } else { 
            return null;
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeException {
        employeeDao.updateEmployee(employee);
    }

    public void deleteEmployee(int id) throws EmployeeException {
        employeeDao.removeEmployee(id);
    }

    @Override
    public List<Employee> getEmployees() throws EmployeeException {
        return employeeDao.fetchEmployees();
    }

    @Override
    public List<Employee> getAllEmployees() throws EmployeeException {
        return employeeDao.fetchAllEmployees();
    }

    /**
     * <p>
     * Checks whether a employee detail is present in employees data
     * </p>
     * @param id                  The ID of the employee to be searched
     * @return                    The found employee else null
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific employee detail.
     */
    private boolean isEmployeePresent(int id) throws EmployeeException {
        boolean isPresent = false;
        List<Employee> employees = getEmployees();
        for (Employee employee: employees) {
            if (employee.getId() == id) {
                isPresent = true;
                break;
            }
        }
        return isPresent;
    }
}