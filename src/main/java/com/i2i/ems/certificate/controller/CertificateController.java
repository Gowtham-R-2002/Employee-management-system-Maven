package com.i2i.ems.certificate.controller;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.certificate.service.CertificateService;
import com.i2i.ems.certificate.service.CertificateServiceImpl;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Employee;
import com.i2i.ems.util.Validator;

/** 
 * <p> Responsible for providing endpoints for 
 * adding and displaying certificates based on the user's choice. 
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public class CertificateController {
    private static Scanner scanner = new Scanner(System.in);
    private static Logger logger = LogManager.getLogger(); 
    private CertificateService certificateService = new CertificateServiceImpl();

    /**
     * <p>
     * Gets choice from the user and performs corresponding operations.
     * </p>
     */
    public void handleChoice() {
        boolean isExited = false;
        System.out.println("\n------------------------\n");
        System.out.println("Certificate Services");
        System.out.println("1. Add an certificate");
        System.out.println("2. Update a certificate");
        System.out.println("3. Delete a certificate");
        System.out.println("4. Display all certificates");
        System.out.println("5. Get employees Certificate wise");
        System.out.println("6. Back");
        int choice = validateAndReturnNumber();
        try {
            switch (choice) {
            case 1:
                createCertificate();
                break;

            case 2:
                updateCertificate();
                break;

            case 3:
                deleteCertificate();
                break;
    
            case 4:
                displayCertificates();
                break;

            case 5:
                validateEmployeesOfCertificate();
                break;
  
            case 6:
                isExited = true;
                break;
                
            default:
                System.out.println("Enter valid choice !");
            }
        } catch (EmployeeException e) {
            isExited = true;
            logger.error("Application exits as exception occured !", e);
            e.printStackTrace();
        }
        if(!isExited) {
            handleChoice();
        }
    }

    /**
     * <p>
     * Displays all available certificates
     * </p>
     * @throws EmployeeException  Happens when error occurs while displaying
     *                            all available certificates.   
     */
    public void displayCertificates() throws EmployeeException {
        if (certificateService.getCertificates().size() == 0) {
            logger.info("No certificates found!");
        } else {
            for (Map.Entry<Integer, Certificate> entry 
                     : certificateService.getCertificates().entrySet()) {
                System.out.println(entry.getKey() + " ---> " 
                                   + entry.getValue().getName());
            }
        }
    }

    /**
     * <p>
     * Displays the available employees in a specific certification
     * </p> 
     * @param certificateId       The id of the specific certificate to be searched. 
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific certificate detail.
     */
    public void displayEmployees(int certificateId) throws EmployeeException {
        Set<Employee> certificates = certificateService
                                     .getCertificateEmployees(certificateId); 
        String format = ("%-5s | %-15s | %-20s | %-15s | %-10s |"
                         + " %-50s | %-50s |");
        System.out.format(format, "ID", "Name", "Age", "Ph.No", 
                          "Department", "Certificate(s)", "Address");
        System.out.println();                    
        if (certificates.size() != 0) {
            for (Employee employee : certificates) {
                if (employee.getIsDeleted() == false) {
                    employee.displayEmployee();
                }
            }
        } else {
            logger.info("No data found for ID :" + certificateId);
        }
    }

    /**
     * <p>
     * Gets Certificate name from user and creates a new Certificate
     * </p>
     * @throws EmployeeException  Happens when error occurs while creating
     *                            a certificate.
     */
    public void createCertificate() throws EmployeeException {
        scanner.nextLine();
        System.out.println("Enter name of the certificate : ");
        String name = scanner.nextLine();
        certificateService.addCertificate(name);
    }

    /**
     * <p>
     * Displays the certificates if and only if minimum of one
     * certificate is available.
     * </p>
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific certificate detail.
     */
    public void validateEmployeesOfCertificate() throws EmployeeException {
        if (certificateService.getCertificates().size() == 0) {
            logger.info("No certificates found!");
        } else {
            System.out.println("Available certificates : ");
            logger.debug("Displaying Certificates...");
            displayCertificates();
            int certificateId = validateAndReturnNumber();
            if (certificateService.getCertificates()
                                  .containsKey(certificateId)) {
                displayEmployees(certificateId);
            } else {
                logger.info("No data found for ID :" + certificateId);
            }
        }
    }

    /**
     * <p>
     * Updates the name of the selected certificate
     * </p> 
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            or updating a specific certificate detail.
     */
    public void updateCertificate() throws EmployeeException {
        if (certificateService.getCertificates().size() == 0) {
            logger.info("No certificates found!");
        } else {
            System.out.println("---Enter Certificate ID---");
            int certificateId = validateAndReturnNumber();
            if (certificateService.getCertificates()
                                  .containsKey(certificateId)) {
                System.out.println("Enter the updated certificate name : ");
                scanner.nextLine();
                String name = scanner.nextLine();
                certificateService.updateCertificate(certificateId, name);
            } else {
                logger.info("No data found for ID :" + certificateId);
            }
        }
    }

    /**
     * <p>
     * Soft Deletes the selected certificate
     * </p> 
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            or deleting a specific certificate detail.
     */
    public void deleteCertificate() throws EmployeeException {
        if (certificateService.getCertificates().size() == 0) {
            logger.info("No certificates found!");
        } else {
            System.out.println("---Enter Certificate ID---");
            int certificateId = validateAndReturnNumber();
            if (certificateService.getCertificates()
                                  .containsKey(certificateId)) {
                certificateService.deleteCertificate(certificateId);
            } else {
                logger.info("No data found for ID :" + certificateId);
            }
        }
    }

    /** 
     * <p>
     * Gets input, validates it and returns the number
     * </p>
     * @return  The validated number input
     */
    public int validateAndReturnNumber() {
        boolean isValid = false;
        do {
            try {
                System.out.println("Enter your Choice : ");
                int validNumber = scanner.nextInt();
                isValid = true;
                return validNumber;
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number !");
                scanner.next();
            }
        } while (!isValid);
        return 0;
    }
}