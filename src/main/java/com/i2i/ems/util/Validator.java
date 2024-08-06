package com.i2i.ems.util;

/** 
 * <p>
 * Contains method for Validation for inputs given by user 
 * </p>
 * @author   Gowtham R
 * @version  1.0
 */
public class Validator {

    /** 
     * Returns boolean value after validation of any String
     *
     * @param name  The String that is to be validated
     * @return      The boolean true if it is a valid String 
     *              else false
     */
    public static boolean isAlphabeticName(String name) {
        return name.matches("^[a-zA-Z ]+$") ? true : false;
    }
}