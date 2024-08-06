package com.i2i.ems.util;

import java.time.LocalDate;
import java.time.Period;

/**
 * <p>
 * Contains method for calculating age of a employee
 * <p>
 * @author   Gowtham R
 * @version  1.0
 */
public class DateUtil {

    /** 
     * Calculates the Age of the Employee comparing the given
     * date and current date
     * 
     * @param   The Employee dateOfBirth value
     * @return  The Age of the Employee in String as Years and Months
     */
    public static String getAge(LocalDate dateOfBirth) {
        Period period = Period.between(dateOfBirth, LocalDate.now());
        return period.getYears() + "y " + period.getMonths() + "m";
  }
}