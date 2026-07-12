package com.store.Util;

import com.store.service.UserService;

public class ValidationUtil {
    public static boolean validateString(String input) throws Exception {
        if (input == null || input.trim().isEmpty()) {
            throw new Exception("Empty String");
        }

        return true;
    }

    public static boolean validateName(String name) throws Exception {
        validateString(name);

        if (!name.matches("^[A-Za-z- ]+{3,30}$"))
            throw new Exception("Name contains invalid characters");

        if (name.trim().replaceAll("-", "").length() < 3)
            throw new Exception("Name must be of at least 3 alphabets");

        return true;
    }

    public static boolean validateMail(String email) throws Exception {
        validateString(email);

        if (email.trim().length() < 13)
            throw new Exception("Gmail Name must be of at least 13 characters");
        if (!email.endsWith("@gmail.com"))
            throw new Exception("Email must end with @gmail.com");
        if (!email.replace("@gmail.com", "").matches("^[a-zA-Z0-9.]+{3,20}$"))
            throw new Exception("Gmail contains invalid characters.");

        return true;
    }

    public static boolean validateUsername(String username, String role) throws Exception {
        validateString(username);

        UserService userService = new UserService();

        if (userService.findUserByUsername(username, role)) {
            throw new Exception("Username Already Taken");
        }

        if (username.trim().length() < 5)
            throw new Exception("Username must have 5 characters");

        if (!username.matches("^[a-zA-Z0-9+.-@]+$"))
            throw new Exception("Invalid Character in username");

        return true;
    }

    public static boolean validateContact(String contact) throws Exception {
        validateString(contact);

        if (!(contact.trim().length() == 11))
            throw new Exception("Contact must be of 11 digits");
        if (!contact.trim().matches("^[0-9]$"))
            throw new Exception("Invalid character used for contact.\nUse only digits.");

        return true;
    }

    public static int validateIntInput(String value) throws Exception {
        if (!value.matches("^[0-9.,]+$"))
            throw new Exception("Invalid int Input");

        return Integer.parseInt(value.replaceAll(".", "").replaceAll(",", ""));
    }

    public static boolean validatePassword(String password) throws Exception {
        validateString(password);

        if (!password.matches("^[a-zA-Z0-9.-`~!@#$%^&*]+{8,20}$"))
            throw new Exception("Illegal Characters in password");

        return true;
    }
}
