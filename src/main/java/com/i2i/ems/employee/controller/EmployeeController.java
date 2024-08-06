package com.i2i.ems.employee.controller;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.certificate.service.CertificateService;
import com.i2i.ems.certificate.service.CertificateServiceImpl;
import com.i2i.ems.department.service.DepartmentService;
import com.i2i.ems.department.service.DepartmentServiceImpl;
import com.i2i.ems.employee.service.EmployeeService;
import com.i2i.ems.employee.service.EmployeeServiceImpl;
import com.i2i.ems.exceptions.EmployeeException;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Certificate;
import com.i2i.ems.model.Department;
import com.i2i.ems.model.Employee;
import com.i2i.ems.util.Validator;

/**
 * <p>
 * Provides endpoint for handling operations on employee such as adding an employee.
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public class EmployeeController {
    private Scanner scanner = new Scanner(System.in);
    private static Logger logger = LogManager.getLogger();
    private CertificateService certificateService = new CertificateServiceImpl();
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private EmployeeService employeeService = new EmployeeServiceImpl();

    /**
     * <p>Gets choice on what operation to do from the user and 
     * handles it
     * </p>
     */
    public void handleChoice() {
        boolean isExited = false;
        System.out.println("\n------------------------\n");
        System.out.println("Select your choice");
        System.out.println("1. Display Employees");
        System.out.println("2. Create Employee");
        System.out.println("3. Display Employee by ID");
        System.out.println("4. Update Employee Details");
        System.out.println("5. Delete an Employee");
        System.out.println("6. Display All Employees");
        System.out.println("7. Add a certificate to an Employee");
        System.out.println("8. Back");
        int choice = validateAndReturnNumber();
        try {
            switch (choice) {
            case 1:
                displayEmployees();
                break;

            case 2:
                createNewEmployee();
                break;

            case 3:
                displaySpecificEmployee();  
                break;

            case 4:
                handleUpdateChoice();
                break;

            case 5:
                deleteEmployee();
                break;  

            case 6:
                displayAllEmployees();
                break;    

            case 7:        
                addCertificateToEmployee();
                break;
  
            case 8:
                isExited = true;
                break;

            default : 
                System.out.println("Enter valid input");    
            }
        } catch (EmployeeException e) {
            isExited = true;
            logger.error("Application exits as an exception occured", e);
            e.printStackTrace();
        }
        if(!isExited) {
            handleChoice();
        }
    }

    /**
     * <p>
     * Displays available departments from the repository
     * </p>
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            available departments.
     */
    public void displayDepartments() throws EmployeeException {
        Map<Integer, Department> departments 
            = departmentService.getDepartments();        
        for (Map.Entry<Integer, Department> entry 
                : departments.entrySet()) {
            System.out.println(entry.getKey() + " ---> "
                               + entry.getValue().getName());
        }
    }

    /**
     * <p>
     * Displays available certificates from the repository
     * </p>
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            available certificates.
     */
    public void displayCertificates() throws EmployeeException {
        Map<Integer, Certificate> certificates
            = certificateService.getCertificates();
        for (Map.Entry<Integer, Certificate> entry 
                : certificates.entrySet()) {
            System.out.println(entry.getKey() + " ---> "
                               + entry.getValue().getName());
        }
    } 

    /**
     * <p>
     * Displays update options to the user.
     * </p> 
     * @throws EmployeeException  Happens when error occurs while
     *                            getting updation choice from the user.
     */
    public void handleUpdateChoice() throws EmployeeException {
        if (employeeService.getEmployees().isEmpty()) {
            logger.info("No employees found!");
        } else { 
            System.out.println("---Enter employee ID---");
            int employeeId = validateAndReturnNumber();
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee != null) {
                System.out.println("Employee found !!!");
                String format = ("%-5s | %-15s | %-20s | %-15s | %-10s |"
                                 + " %-50s | %-10s |");
                System.out.format(format, "ID", "Name", "Age", "Ph.No", 
                                  "Department", "Certificate(s)", "City");
                System.out.println();
                employee.displayEmployee();
                System.out.println("What to Update ?");
                System.out.println("1. Name\n2. Department\n3.Date of Birth\n"
                                   + "4.Phone Number\n5. Address");
                int updationChoice = validateAndReturnNumber();
                updateEmployee(updationChoice, employee);
            } else {
                logger.info("No employees found with ID : " + employeeId);
            }                    
        }
    }

    /**
     * <p>
     * Updates an specific available employee detail
     * </p>
     *
     * @param updationChoice  The choice selected 
     * @param employee        The Employee to be updated
     * @throws EmployeeException  Happens when error occurs while updating
     *                            an employee detail.
     */
    public void updateEmployee(int updationChoice, Employee employee) throws EmployeeException {
        switch(updationChoice) {
        case 1:
            String name = readEmployeeName();
            employee.setName(name);
            break;
 
        case 2:
            System.out.println("Enter department ID");
            displayDepartments();
            int departmentId = validateAndReturnNumber();
            employee.setDepartment(departmentService
                                       .getDepartment(departmentId));
            break;

        case 3:
            LocalDate dateOfBirth = validateDateOfBirth();
            employee.setDateOfBirth(dateOfBirth);
            break;

        case 4:
            long phoneNumber = validatePhoneNumber();
            employee.setPhoneNumber(phoneNumber);
            break;

        case 5:
            scanner.nextLine();
            System.out.println("Enter Door number : ");
            String doorNumber = scanner.nextLine();
            System.out.println("Enter Locality / Area :");
            String locality = scanner.nextLine();
            String city = readCity();
            Address address = new Address(doorNumber, locality, city);
            employee.setAddress(address);
            break;

        default:
            System.out.println("Enter valid choice");
        }
        employeeService.updateEmployee(employee); 
    }

    /** 
     * <p>
     * Displays Employees that are present.
     * </p>
     * 
     * @return  The Validated choice of the user
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific certificate detail.
     */
    public void displayEmployees() throws EmployeeException {
        if (employeeService.getEmployees().isEmpty()) {
            logger.info("No employees found!");
        } else {
            String format = ("%-5s | %-15s | %-20s | %-15s | %-10s |"
                             + " %-50s | %-50s |");
            System.out.format(format, "ID", "Name", "Age", "Ph.No", 
                              "Department", "Certificate(s)", "Address");
            System.out.println();
            for (Employee employee : employeeService.getEmployees()) {
                employee.displayEmployee();
                System.out.println();
            }
        }
     }
    
    /** 
     * <p> 
     * Creates new Employee with user input with validations 
     * </p>
     * @throws EmployeeException  Happens when error occurs while adding
     *                            an employee
     */
    public void createNewEmployee() throws EmployeeException {
        if (departmentService.getDepartments().size() == 0) {
            logger.info("No Departments found!\nAdd a department first!");
        } else {
            String name = readEmployeeName();
            System.out.println("Select Department");
            displayDepartments();
            int departmentId = getDepartmentId();
            LocalDate dateOfBirth = validateDateOfBirth();
            long phoneNumber = validatePhoneNumber();
            scanner.nextLine();
            System.out.println("Enter Door number : ");
            String doorNumber = scanner.nextLine();
            System.out.println("Enter Locality / Area :");
            String locality = scanner.nextLine();
            String city = readCity();
            employeeService.addEmployee(name, departmentId, dateOfBirth,
                                        phoneNumber, doorNumber, locality,
                                        city);
        }
    }

    /** 
     * <p>
     * Checks whether the given department ID is present
     * </p> 
     *
     * @return  The valid department ID
     * @throws EmployeeException  Happens when error occurs while the user 
     *                            input doesn't present in available departments.
     */
    public int getDepartmentId() throws EmployeeException {
        int departmentId = validateAndReturnNumber();
        if (!departmentService.getDepartments().containsKey(departmentId)) {
            System.out.println("Enter valid ID !");
            displayDepartments();
            return getDepartmentId();
        }
        return departmentId;
    }

    /** 
     * <p>
     * Displays a Specific Employee of the given ID from the user
     * </p>
     * 
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            a specific employee detail.
     */
    public void displaySpecificEmployee() throws EmployeeException {
        if (employeeService.getEmployees().isEmpty()) {
            logger.info("No employees found!");
        } else {
            System.out.println("---Enter employee ID---");
            int employeeId = validateAndReturnNumber();
            if (employeeService.getEmployeeById(employeeId) != null) {
                String format = ("%-5s | %-15s | %-20s | %-15s | %-10s |"
                                 + " %-50s | %-50s |");
                System.out.format(format, "ID", "Name", "Age", "Ph.No", 
                                  "Department", "Certificate(s)", "Address");
                System.out.println();
                employeeService.getEmployeeById(employeeId).displayEmployee();
            } else {
                logger.info("No employees found with ID : " + employeeId);
            }
        }
    }

    /** 
     * <p>
     * Soft deletes an Employee from the repository
     * </p>
     * @throws EmployeeException  Happens when error occurs while deleting
     *                            a specific employee.
     */
    public void deleteEmployee() throws EmployeeException {
        if (employeeService.getEmployees().isEmpty()) {
            logger.info("No employees found!");
        } else { 
            System.out.println("---Enter employee ID---");
            int employeeId = validateAndReturnNumber();
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee != null) {
                employeeService.deleteEmployee(employeeId);
            } else {
                logger.info("No employees found with ID : " + employeeId);
            } 
        } 
    }

    /** 
     * <p>
     * Displays all Employees regardless of deleted Employees
     * </p>
     * @throws EmployeeException  Happens when error occurs while fetching
     *                            all available employees.
     */
    public void displayAllEmployees() throws EmployeeException {
        if (employeeService.getAllEmployees().isEmpty()) {
            logger.info("No employees found!");
        } else {
            String format = ("%-5s | %-15s | %-20s | %-15s | %-10s |"
                             + " %-50s | %-50s |");
            System.out.format(format, "ID", "Name", "Age", "Ph.No", 
                              "Department", "Certificate(s)", "Address");
            System.out.println();
            for (Employee employee : employeeService.getAllEmployees()) {
                 employee.displayEmployee();
            }
        }            
    }

    /** 
     * <p>
     * Calls validateAndAddCertificate and adds the Certificate.
     * </p>
     *
     * @throws EmployeeException  Happens when error occurs while adding an employee
     *                            to a specific certificate.
     */
    public void addCertificateToEmployee() throws EmployeeException {
        if (employeeService.getEmployees().isEmpty()) {
            logger.info("No employees found!");
        } else if (certificateService.getCertificates().size() == 0) {
            logger.info("No certificate found!\nAdd a certificate first.");
        } else { 
            System.out.println("---Enter employee ID---");
            int employeeId = validateAndReturnNumber();
            Employee employee = employeeService.getEmployeeById(employeeId);
            if (employee != null) { 
                System.out.println("Employee with ID : " + employee.getId() 
                                   + " Name : " + employee.getName() + "found !");
                System.out.println("Available Certificates : ");
                displayCertificates();
                int certificateId = validateAndReturnNumber();
                validateAndAddCertificate(certificateId, employee);
            } else {
                logger.info("No employees found with ID : " + employeeId);
            }                   
        }
    }

    /**
     * <p>
     * Adds the Certificate to the Employee
     * </p>
     *
     * @param certificateId  The Id of the Certificate
     * @param employee       The Employee selected to add Certificate
     * @throws EmployeeException  Happens when error occurs while adding an employee
     *                            to a specific certificate.
     */
    public void validateAndAddCertificate(int certificateId, Employee employee) throws EmployeeException {
        if (certificateService.getCertificates().containsKey(certificateId)) {
            boolean isPresent = false;
            for (Certificate certificate : employee.getCertificates()) {
                if (certificate.getName()
                               .equals(certificateService
                               .getCertificate(certificateId)
                              .getName())) {
                    isPresent = true;
                }
            }
            if(!isPresent) {
                certificateService.addEmployee(certificateService.getCertificate(certificateId), employee);
            } else { 
                logger.info("Certificate ID : " + certificateId 
                            + " already exists for Employee ID :"
                            + employee.getId());
            }                
        } else {
            logger.info("Enter valid input");
        }
    }

    /**
     * <p>
     * Gets Employee name and validate it
     * </p>
     * 
     * @return  The name of the Employee after validation
     */
    public String readEmployeeName() {
        String name = "";
        scanner.nextLine();
        while(!Validator.isAlphabeticName(name)) {
            System.out.println("Enter Employee name : ");
            name = scanner.nextLine();
            if (!Validator.isAlphabeticName(name)) {
                System.out.println("Enter alphabets only !");
            }
        }
        return name;
    }

    /**
     * <p>
     * Gets Employee city and validate it
     * </p>
     * 
     * @return  The city name of the Employee after validation
     */
    public String readCity() {
        String city = "";
        while(!Validator.isAlphabeticName(city)) {
            System.out.println("Enter Employee city : ");
            city = scanner.nextLine();
            if (!Validator.isAlphabeticName(city)) {
                System.out.println("Enter alphabets only !");
            }
        }
        return city;
    }

    /**
     * <p>
     * Gets user input and validates it whether it is a number or not
     * </p>
     * 
     * @return  The user input number after validation
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

    /**
     * <p>
     * Gets Employee phone number and validates it
     * </p>
     * 
     * @return  The phone number of the Employee after validation
     */
    public long validatePhoneNumber() {
        boolean isValid = false;
        do {
            try {
                System.out.println("Enter Phone Number : ");
                long phoneNumber = scanner.nextLong();
                if (Long.toString(phoneNumber).length() == 10) {
                    isValid = true;
                    return phoneNumber;
                } else {
                    System.out.println("Phone Number must be 10 digit long");
                    return validatePhoneNumber();
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid number !");
                scanner.next();
            }
        } while (!isValid);
        return 0;    
    }

   /**
    * <p>Gets dateOfBirth as String type and convert it
    * into LocalDate type with dd-MM-yyyy format
    * 
    * Checks for any discrepency with respect to correct date
    * format and further check if years difference between today's 
    * date and user provided date is strictly greater than 18 and
    * less than 60.
    * </p>
    *
    * @return  The Date Of Birth which is validated.
    */
   public LocalDate validateDateOfBirth() {
        LocalDate validDateOfBirth = null;
        boolean isValidInput = false;
        while (!isValidInput) {
        scanner.nextLine();
            try {
                System.out.println("Enter Date of Birth (DD/MM/YYYY) :");
                String dateOfBirth = scanner.next();
                DateTimeFormatter dateFormatter = DateTimeFormatter
                                                  .ofPattern("dd/MM/yyyy");
                LocalDate validDOB = LocalDate.parse(dateOfBirth, 
                                                           dateFormatter);
                if ((LocalDate.now().compareTo(validDOB) > 18 )
                        && (LocalDate.now().compareTo(validDOB) < 60)) {
                    isValidInput = true;   
                    validDateOfBirth = validDOB;
                } else {
                    System.out.println("Age must be greater than 18 "
                                       + "and less than 60" );
                }
            } catch (Exception e) {
                System.out.println("Enter a valid Date of Birth !");
            }
        }
        return validDateOfBirth;
    }
}