package com.i2i.ems.certificate.dao;

import java.util.Map;
import java.util.Set;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;

/**
 * <p>
 * Handles operations on certificate such as fetch, create, etc.
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public interface CertificateDao {

    /**
     * <p>
     * Creates a certificate and adds it to the collection or repository
     * with ID assigned as auto-incremented value.
     * </p>
     *
     * @param name               The name of the Certificate to be created
     * @throw EmployeeException  Exception thrown when adding Certificate 
                                 has some issues.
     */

    public void createCertificate(String name) throws EmployeeException;

    /**
     * <p>
     * Gets a specific Certificate details from available certificates.
     * </p>
     *
     * @param id                 The ID of the certificate to be found
     * @return                   The found certificate
     * @throw EmployeeException  Exception thrown when fetching a certificate 
                                 has some issues.
     */
    public Certificate fetchCertificate(int id) throws EmployeeException;

    /**
     * <p>
     * Adds an Employee to the Certificate Employees
     * Also adds the Certificate to the Employee certificates to make association.
     * </p>
     *
     * @param id                 The Certificate to be added
     * @param employee           The Employee to be added to the collection
     * @throw EmployeeException  Exception thrown when adding employee to a certificate
     *                           has some issues.
     */
    public void addEmployee(Certificate certificate, Employee employee) throws EmployeeException;

    /**
     * <p>
     * Gets all certificates from available.
     * </p>
     *
     * @param <Integer>          The ID of the certificate
     * @param <Certificate>      The Certificate associated with the ID
     * @return                   The Certificates Data
     * @throw EmployeeException  Exception thrown when fetching all certificates
                                 has some issues.
     */
   public Map<Integer, Certificate> fetchCertificates() throws EmployeeException;

    /**
     * <p>
     * Updates a specific Certificate name
     * </p>
     * @param certificateId       The ID of the specific certificate to be updated
     * @param name                The updated certificate name
     * @throws EmployeeException  Happens when error occurs while updating
     *                            a specific certificate detail.
     */
    public void updateCertificate(int certificateId, String name) throws EmployeeException;

    /**
     * <p>
     * Deletes a specific Certificate name
     * </p>
     * @param certificateId       The ID of the specific certificate to be deleted
     * @throws EmployeeException  Happens when error occurs while deleting
     *                            a specific certificate detail.
     */
    public void deleteCertificate(int certificateId) throws EmployeeException;

    /**
     * <p>
     * Gets employees of a particular associated with a particular certificate.
     * </p>
     * @param id                  The ID of the certificate from where employees
                                  need to be fetched.
     * @return                    The employees associated with a particular certificate.
     * @throws EmployeeException  Happens when error occurs while getting employees of
     *                            a specific certificate.
     */     
    public Set<Employee> getCertificateEmployees(int id) throws EmployeeException;
}