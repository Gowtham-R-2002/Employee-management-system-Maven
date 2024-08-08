package com.i2i.ems.employee.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.query.Query; 
import org.hibernate.Session; 
import org.hibernate.Transaction;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.helper.HibernateHelper;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void createEmployee(Employee employee) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(employee);
            logger.info("Employee created with ID : {}", id);
            transaction.commit();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while adding empoyee of name : " + employee.getName(), e);
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
            logger.info("Employee updated with ID : {}", employee.getId());
        } catch (HibernateException e) {
            throw new EmployeeException("Error while updating employee of ID : " + employee.getId(), e);
        }
    } 

    @Override
    public void removeEmployee(int id) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            String deleteQuery = "UPDATE Employee SET isDeleted = :isDeleted WHERE id = :id";
            Query<?> query = session.createQuery(deleteQuery);
            query.setParameter("isDeleted", true);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            logger.info("Employee Deleted with ID : {}", id);
        } catch (HibernateException e) {
            throw new EmployeeException("Error while deleting employee of id : " + id, e);
        }
    }

    @Override
    public Employee fetchEmployeeById(int id) throws EmployeeException {
        Employee employee;
        try (Session session = HibernateHelper.getFactory().openSession()) {
            employee = session.get(Employee.class, id);
        } catch (HibernateException e) {
            throw new EmployeeException("Error while fetching employee of ID : " + id, e);
        }
        return employee;    
    }

    @Override
    public List<Employee> fetchEmployees() throws EmployeeException {
        List<Employee> employees;
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Query<Employee> query = session.createQuery("FROM Employee WHERE isDeleted = :isDeleted", Employee.class)
                    .setParameter("isDeleted", false);
            employees = query.list();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while fetching employees", e);
        }
        return employees;
    }

    @Override
    public List<Employee> fetchAllEmployees() throws EmployeeException {
        List<Employee> employees;
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Query<Employee> query = session.createQuery("FROM Employee", Employee.class);
            employees = query.list();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while fetching all employees", e);
        }
        return employees;
    }
}