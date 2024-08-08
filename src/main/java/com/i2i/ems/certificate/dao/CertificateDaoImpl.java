package com.i2i.ems.certificate.dao;

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
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;

public class CertificateDaoImpl implements CertificateDao {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void createCertificate(String name) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Certificate certificate = new Certificate(name);
            Integer id = (Integer) session.save(certificate);
            logger.info("Certificate created with ID : {}", id);
            transaction.commit();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while adding certificate of name : " + name, e);
        }
    }

    @Override
    public Certificate fetchCertificate(int id) throws EmployeeException {
        Certificate certificate;
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Query<Certificate> query = session.createQuery("from Certificate where id = :id AND isDeleted = 0", Certificate.class);
            query.setParameter("id", id);
            certificate = query.uniqueResult();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while fetching certificate of id : " + id, e);
        }
        return certificate;
    }

    @Override
    public void addEmployee(Certificate certificate, Employee employee) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Employee employeeFromDb = session.get(Employee.class, employee.getId());
            Certificate certificateFromDb = session.get(Certificate.class, certificate.getId());
            Set<Certificate> certificates = employeeFromDb.getCertificates();
            Set<Employee> employees = certificateFromDb.getEmployees();
            certificates.add(certificateFromDb);
            employees.add(employeeFromDb);
            session.saveOrUpdate(employeeFromDb);
            session.saveOrUpdate(certificateFromDb);
            transaction.commit();
            logger.info("Certificate with ID : {} assigned to Employee with ID : {}", certificate.getId(), employee.getId());
        } catch (HibernateException e) {
            throw new EmployeeException("Error while adding certificate " + certificate.getName()
                    + "to employee id : " + employee.getName(), e);
        }
    }
 
    @Override
    public Map<Integer, Certificate> fetchCertificates() throws EmployeeException {
        Map<Integer, Certificate> certificates = new HashMap<>();
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Query<Certificate> query = session.createQuery("from Certificate where isDeleted = 0", Certificate.class);
            List<Certificate> certificatesFromDb = query.list();
            for (Certificate certificate : certificatesFromDb) {
                certificates.put(certificate.getId(), certificate);
            }
        } catch (HibernateException e) {
            throw new EmployeeException("Error while fetching available certificates !", e);
        }
    return certificates;
    }

    @Override
    public void updateCertificate(int certificateId, String name) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("UPDATE Certificate SET name = :name WHERE id = :id");
            query.setParameter("id", certificateId);
            query.setParameter("name", name);
            int status = query.executeUpdate();
            if (status == 1) {
                logger.info("Update success for Certificate ID : " + certificateId);
            } else {
                logger.warn("Update failed for Certificate ID : " + certificateId);
            }
            transaction.commit();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while updating certificate of id : " + certificateId, e);
        }
    }

    @Override
    public void deleteCertificate(int certificateId) throws EmployeeException {
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Transaction transaction = null;
            transaction = session.beginTransaction();
            Query<?> query = session.createQuery("UPDATE Certificate SET isDeleted = :isDeleted WHERE id = :certificateId");
            query.setParameter("isDeleted", true);
            query.setParameter("certificateId", certificateId);
            int status = query.executeUpdate();
            if (status == 1) {
                logger.info("Delete success for Certificate ID : {}", certificateId);
            } else {
                logger.info("Delete failed for Certificate ID : {}", certificateId);
            }
            transaction.commit();
        } catch (HibernateException e) {
            throw new EmployeeException("Error while deleting certificate of id : " + certificateId, e);
        }
    }

    @Override
    public Set<Employee> getCertificateEmployees(int id) throws EmployeeException {
        Set<Employee> employees = new HashSet<>();
        try (Session session = HibernateHelper.getFactory().openSession()) {
            Certificate certificate = session.get(Certificate.class, id);
            if (certificate != null) {
                employees = certificate.getEmployees();
            }
        } catch (HibernateException e) {
            throw new EmployeeException("Error while getting employees of certificate of id : " + id, e);
        }
        return employees;
    }
}