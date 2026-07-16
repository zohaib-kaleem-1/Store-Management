package com.store.Util;

import com.store.Exception.IllegalFormatException;

import com.store.service.UserService;

/**
 * 
 * ValidationUtil
 * 
 * Utility for validating user inputs.
 */

public class ValidationUtil {
    /**
     * Validate input string must not be empty or null
     * 
     * @param input       the input string to be checked
     * @param nameOfField the asset the string represents
     * @return returns true if string is not null or invalids
     * @throws Exception throws exception if value is null.
     */
    public static boolean validateString(String input, String nameOfField) throws IllegalFormatException {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalFormatException(nameOfField + " Field Can't be empty.");
        }

        return true;
    }

    /**
     * Validate name like
     * can't contain symbol, digits etc
     * 
     * @param name the input string to validate
     * @return true if follows all conditions
     * @throws Exception
     */
    public static boolean validateName(String name) throws Exception {
        validateString(name, "Name");

        if (!name.matches("^[A-Za-z\\- ]{3,30}$"))
            throw new IllegalFormatException("Name contains invalid characters");

        if (name.trim().replaceAll("-", "").length() < 3)
            throw new Exception("Name must be of at least 3 alphabets");

        return true;
    }

    /**
     * Validate email cheking
     * length > 13
     * must be only gmail
     * 
     * 
     * @param email input to validate
     * @return true if value is email
     * @throws IllegalFormatException throws exception if any error
     */
    public static boolean validateMail(String email) throws IllegalFormatException {
        validateString(email, "Email");

        // must be greater then 13
        if (email.trim().length() < 13)
            throw new IllegalFormatException("Gmail Name must be of at least 13 characters");

        // must be gmail
        if (!email.endsWith("@gmail.com"))
            throw new IllegalFormatException("Email must end with @gmail.com");

        // must have only allowed character
        // small letter, capital letter, .
        // length of name must be 3-20 resulting a max email length of 30
        if (!email.replace("@gmail.com", "").matches("^[a-zA-Z0-9.]{3,20}$"))
            throw new IllegalFormatException("Gmail contains invalid characters.");

        return true;
    }

    /**
     * Validate username by checking
     * avalibilty (If anyone with same role has that username)
     * length of username msut be between 5 - 30
     * checking format allowing only alphabets, numbers, specific symbols
     * 
     * 
     * @param username input to validate
     * @param role     the role against which username is checked in database
     * @return true if follows all conditions
     * @throws Exception throws exception if found any error
     */
    public static boolean validateUsername(String username, String role) throws Exception {
        validateString(username, "Username");

        UserService userService = new UserService();

        if (userService.findUserByUsername(username, role)) {
            throw new Exception("Username Already Taken");
        }

        if (username.trim().length() < 5)
            throw new IllegalFormatException("Username must have 5 characters");

        if (username.trim().length() > 30)
            throw new IllegalFormatException("Username must have less than 30 characters");

        if (!username.matches("^[a-zA-Z0-9+.-@]+$"))
            throw new IllegalFormatException("Invalid Character in username");

        return true;
    }

    /**
     * Validating contact by
     * checking length must be 11
     * only numeric keywords allowed
     * 
     * @param contact input to check
     * @return true if follows condition
     * @throws Exception throws exception in caller
     */
    public static boolean validateContact(String contact) throws Exception {
        validateString(contact, "Contact");

        if (!(contact.trim().length() == 11))
            throw new Exception("Contact must be of 11 digits");

        if (!(contact.trim().matches("^[0-9]+$")))
            throw new IllegalFormatException("Invalid character used for contact.\nUse only digits.");

        return true;
    }

    /**
     * Validating if string is convertable to int or not
     * 
     * @param value the value to be converted
     * @return integer value if it follows all condition
     * @throws Exception if encountered any error
     * 
     */
    public static int validateIntInput(String value) throws Exception {

        // Must only have digits
        if (!value.matches("^[0-9]$"))
            throw new Exception("Invalid int Input.\nMust have only digits.");

        int intValue = Integer.parseInt(value);

        if (intValue < 1)
            throw new Exception("Value must be greater then 1");

        return intValue;
    }

    /**
     * Validating password like
     * must have 1 symbol, uppercase letter, lowercase letter, digit
     * 
     * @param password password value to validate
     * @return true if follows all condition
     * @throws Exception throws exception if found any issue
     */
    public static boolean validatePassword(String password) throws Exception {
        validateString(password, "Password");

        // matches the length requirement 8 - 20
        if (password.length() < 8)
            throw new Exception("Length must be greater then 8.");

        else if (password.length() > 20)
            throw new Exception("Length must be less then 20 character.");

        /*
         * all characters are :
         * lowercase, uppercase, selective symbols, digits
         */
        else if (!password.matches("^[a-zA-Z0-9.`~!@#$%^&*]+$"))
            throw new IllegalFormatException("Illegal Characters in password");

        // must have at least one digit
        if (!password.matches(".*[0-9].*"))
            throw new IllegalFormatException("Password must have at least 1 number.");

        // must have at least one small letter
        if (!password.matches(".*[a-z].*"))
            throw new IllegalFormatException("Password must have at least 1 small alphabet.");

        // must have at least one capital letter
        if (!password.matches(".*[A-Z].*"))
            throw new IllegalFormatException("Password must have at least 1 large alphabet.");

        // must have at least one selective symbol
        if (!password.matches(".*[.`~!@#$%^&*].*"))
            throw new IllegalFormatException("Password must have at least 1 symbol.");

        return true;
    }
}
