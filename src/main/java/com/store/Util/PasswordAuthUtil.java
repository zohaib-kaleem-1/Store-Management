package com.store.Util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * PasswordEncryptionUtil
 * 
 * For hashing user password and verifying during login
 */
public class PasswordAuthUtil {
    /**
     * For hashing password
     * Returns the hashed password to store in database while registration
     * 
     * @param password giving password to hash
     * @return returns the hash of password
     */
    public static String encoder(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * for verifying password while login
     * 
     * @param password inputs the user password to verify
     * @param hash     the hash the function generated
     * @return returns true if password matched
     */
    public static boolean verifyPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
