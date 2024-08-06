package com.i2i.ems.department.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.i2i.ems.department.dao.DepartmentDao;
import com.i2i.ems.department.dao.DepartmentDaoImpl;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

public class DepartmentServiceImpl implements DepartmentService {
    DepartmentDao departmentDao = new DepartmentDaoImpl();
    
    @Override 
    public void addDepartment(String name) throws EmployeeException {
        departmentDao.addDepartment(name);
    }

    @Override
    public Department getDepartment(int id) throws EmployeeException {
        return departmentDao.getDepartment(id);
    }

    @Override
    public Map<Integer, Department> getDepartments() throws EmployeeException {
        return departmentDao.getDepartments();
    }

    @Override
    public void updateDepartment(int departmentId, String name) throws EmployeeException {
        departmentDao.updateDepartment(departmentId, name);
    }

    @Override
    public void deleteDepartment(int departmentId) throws EmployeeException {
        departmentDao.deleteDepartment(departmentId);
    }

    @Override
    public Set<Employee> getDepartmentEmployees(int id) throws EmployeeException {
        return departmentDao.getDepartmentEmployees(id);
    }
}

