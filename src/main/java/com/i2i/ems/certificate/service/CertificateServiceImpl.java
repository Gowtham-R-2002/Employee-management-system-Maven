package com.i2i.ems.certificate.service;

import java.util.Map;
import java.util.Set;

import com.i2i.ems.certificate.dao.CertificateDao;
import com.i2i.ems.certificate.dao.CertificateDaoImpl;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;

public class CertificateServiceImpl implements CertificateService {
    CertificateDao certificateDao = new CertificateDaoImpl();

    @Override
    public void addCertificate(String name) throws EmployeeException {
            certificateDao.createCertificate(name);
    }

    @Override
    public Certificate getCertificate(int id) throws EmployeeException{
        return certificateDao.fetchCertificate(id);        
    }

    @Override  
    public Map<Integer, Certificate> getCertificates() throws EmployeeException {
        return certificateDao.fetchCertificates();
    } 

    @Override
    public void addEmployee(Certificate certificate, Employee employee) throws EmployeeException {
        certificateDao.addEmployee(certificate, employee);
    }

    @Override
    public void updateCertificate(int certificateId, String name) throws EmployeeException {
        certificateDao.updateCertificate(certificateId, name);
    }

    @Override
    public void deleteCertificate(int certificateId) throws EmployeeException {
        certificateDao.deleteCertificate(certificateId);
    }

    public Set<Employee> getCertificateEmployees(int id) throws EmployeeException{
        return certificateDao.getCertificateEmployees(id);
    }
}