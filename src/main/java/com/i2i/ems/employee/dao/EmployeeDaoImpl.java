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
    private static Logger logger = LogManager.getLogger();

    @Override
    public void createEmployee(Employee employee) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Integer id = (Integer) session.save(employee);
            logger.info("Employee created with ID : " + id);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while adding empoyee of name : " + employee.getName(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(employee);
            transaction.commit();
            logger.info("Employee updated with ID : " + employee.getId());
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while updating employee of ID : " + employee.getId(), e);
        } finally {
            session.close();
        }
    } 

    @Override
    public void removeEmployee(int id) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String deleteQuery = "UPDATE Employee SET isDeleted = :isDeleted WHERE id = :id";
            Query<?> query = session.createQuery(deleteQuery);
            query.setParameter("isDeleted", true);
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
            logger.info("Employee Deleted with ID : " + id);
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while deleting employee of id : " + id, e);
        } finally {
            session.close();
        }
    }

    @Override
    public Employee fetchEmployeeById(int id) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        Employee employee = null;
        try {
            transaction = session.beginTransaction();
            employee = session.get(Employee.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while fetching employee of ID : " + id, e);
        } finally {
            session.close();
        }
        return employee;    
    }

    @Override
    public List<Employee> fetchEmployees() throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;   
        List<Employee> employees = new ArrayList<>();
        try {
            transaction = session.beginTransaction(); 
            Query<Employee> query =  session.createQuery("FROM Employee WHERE isDeleted = :isDeleted", Employee.class)
                                            .setParameter("isDeleted", false);
            employees = query.list();                                   
            transaction.commit();      
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while fetching employees", e);
        } finally {
            session.close();
        }  
        return employees;
    }

    @Override
    public List<Employee> fetchAllEmployees() throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;  
        List<Employee> employees = new ArrayList<>();
        try {
            transaction = session.beginTransaction();
            Query<Employee> query = session.createQuery("FROM Employee", Employee.class);  
            employees = query.list();                                   
            transaction.commit();             
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while fetching all employees", e);
        } finally {
            session.close();
        }  
        return employees;
    }
}