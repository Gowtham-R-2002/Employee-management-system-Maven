package com.i2i.ems.helper;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.i2i.ems.exceptions.EmployeeException;

/**
 * Builds SessionFactory object for connecting via Hibernate
 * @author  Gowtham R
 * @version 1.0
 */
public class HibernateHelper {
    private static SessionFactory factory = null; 

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (HibernateException e) {
            System.out.println("Failed to create sessionFactory object." + e.getMessage()); 
        }
    }

    /**
     * <p>
     * Returns the object of the SessionFactory created
     * @return  The object of the sessionfactory
     * <p>
     */
    public static SessionFactory getFactory() { 
       return factory;
    }  

    /**
     * <p>
     * Closes the already open SessionFactory
     * <p>
     * @throws EmployeeException  Thrown when closing the sessionfactory
                                  object close has some issues.
     */
    public static void closeFactory() throws EmployeeException {
        try {
            factory.close();
        } catch (HibernateException e) {
            throw new EmployeeException("Can't close Factory !", e);
        }
    }
}