package com.i2i.ems.certificate.service;

import java.util.Map;
import java.util.Set;

import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;

/** 
 * <p>
 * Provides methods for CRUD operations in certificates
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public interface CertificateService {

    /** 
     * <p>
     * Adds a certificate to the available certificates.
     * </p>
     * 
     * @param name  The name of the Certificate to be added
     * @throws EmployeeException  Happens when error occurs while adding
     *                            a certificate detail.
     */
    public void addCertificate(String name) throws EmployeeException;

    /**
     * Gets a specific Certificate from the available certificates
     *
     * @param id  The ID of the certificate to be searched
     * @return    The found Certificate 
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific certificate detail.
     */
    public Certificate getCertificate(int id) throws EmployeeException;

    /**
     * Gets all available certificates.
     * 
     * @param <Integer>      The ID of the Certificate
     * @param <Certificate>  The Certificate assigned to the specific ID
     * @return               All available certificates from the Certificate
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            all certificate details.
     */
    public Map<Integer, Certificate> getCertificates() throws EmployeeException;

    /**
     * Adds an employee to the Certificate and vice versa
     * 
     * @param certificate        The Certificate to be added
     * @param employee  The Employee to be added
     * @throws EmployeeException  Happens when error occurs while adding
     *                            a employee to certificate.
     */
    public void addEmployee(Certificate certificate, Employee employee) throws EmployeeException;

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
     * Deletes a specific Certificate
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