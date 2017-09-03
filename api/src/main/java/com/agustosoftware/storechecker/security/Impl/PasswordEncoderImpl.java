package com.agustosoftware.storechecker.security.Impl;

import com.agustosoftware.storechecker.security.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {

    @Override
    public String hashPassword(String password_plaintext) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return(BCrypt.hashpw(password_plaintext, salt));
    }

    @Override
    public boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;
        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }

}
