package com.agustosoftware.storechecker.security;

public interface PasswordEncoder {

    /**
     * Method to hash user passwords.
     *
     * @param password
     * @return
     */
    String hashPassword(String password);

    /**
     * Verifies a plain text password and a hash password
     *
     * @param password
     * @param hashPassword
     * @return
     */
    boolean checkPassword(String password, String hashPassword);
}
