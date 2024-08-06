package com.i2i.ems.department.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.helper.HibernateHelper;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;

/** 
 * <p>
 * Handles operations on departments such as creating a department, etc.
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public class DepartmentDaoImpl implements DepartmentDao {
    private static Logger logger = LogManager.getLogger();

    @Override
    public void addDepartment(String name) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Department department = new Department(name);
            Integer id = (Integer) session.save(department);
            logger.info("Department generated with ID : " + id);
            transaction.commit();
            logger.debug("Entering UniqueID generation stage...");
            generateUniqueId(id);
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while adding department of name : " + name, e);
        } finally {
            session.close();
        }        
    }

    @Override
    public Department getDepartment(int id) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        Department department = null;
        try {
            transaction = session.beginTransaction();
            department = session.get(Department.class, id);
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while fetching department of id : " + id, e);
        } finally {
            session.close();
        } 
        return department;
    }

    @Override
    public Map<Integer, Department> getDepartments() throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Map<Integer, Department> departments = new HashMap<>();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Department> query = session.createQuery("FROM Department", Department.class);
            List<Department> departmentsFromDb = query.list();
            for (Department department : departmentsFromDb) {
                departments.put(department.getId(), department);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while fetching available departments : ", e);
        } finally {
            session.close();
        }
        return departments;
    }

    public Set<Employee> getDepartmentEmployees(int id) throws EmployeeException {
        Set<Employee> employees = new HashSet<>();
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            if (department != null) {
                employees = department.getEmployees();
            }
            transaction.commit();            
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while fetching employees of Department Id : " + id, e);
        } finally {
            session.close();
        }
        return employees;
    }

    @Override
    public void updateDepartment(int departmentId, String name) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("UPDATE Department SET name = :name WHERE id = :id");
            query.setParameter("id", departmentId);
            query.setParameter("name", name);
            int status = query.executeUpdate();
            if(status == 1) {
                logger.info("Update success for Department ID : " + departmentId);
            } else {
                logger.warn("Update failed for Department ID : " + departmentId);       
            }           
            transaction.commit();
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while updating certificate of id : " + departmentId, e);
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteDepartment(int departmentId) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("DELETE FROM Department WHERE id = :id");
            query.setParameter("id", departmentId);
            query.executeUpdate();          
            transaction.commit();
            logger.info("Department with ID : " + departmentId + " deleted!");
        } catch (HibernateException e) {           
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error while deleting department of id : " + departmentId, e);
        } finally {
            session.close();
        }
    }

    /** 
     * <p>
     * Generates Unique Id for each department
     * Eg : If department ID is 1, then Unique id is generated as "D001"
     * </p>
     * @param id                  The ID of the Department created
     * @throws EmployeeException  Exception thrown when issues occur while 
     *                            generating the unique id for the department 
     */
    private void generateUniqueId(int id) throws EmployeeException {
        Session session = HibernateHelper.getFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Department department = session.get(Department.class, id);
            StringBuilder uniqueId = new StringBuilder("D");
            if (id < 9) {
                uniqueId.append("00");
            } else if (id < 99) {
                uniqueId.append("0");
            }            
            uniqueId.append(String.valueOf(id));
            department.setUniqueId(uniqueId.toString());
            session.saveOrUpdate(department);
            transaction.commit();
            logger.info("Unique ID generated : " + uniqueId
                        + " for Department ID " + id);
        } catch (HibernateException e) {
            if(transaction != null) {
                transaction.rollback();
            }
            throw new EmployeeException("Error generating unique code !", e);
        } finally {
            session.close();
        }
    }
}