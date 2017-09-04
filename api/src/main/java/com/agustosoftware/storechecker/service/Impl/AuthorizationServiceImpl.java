package com.agustosoftware.storechecker.service.Impl;

import com.agustosoftware.storechecker.domain.entity.Role;
import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.exception.UnauthorizedException;
import com.agustosoftware.storechecker.security.PasswordEncoder;
import com.agustosoftware.storechecker.service.AuthorizationService;
import com.agustosoftware.storechecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Component
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String NOT_AUTHORIZED = "Not Authorized";

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User checkAndGetCallerFromAuthorizationHeader(String authorizationHeader) throws UnauthorizedException {
        try {
            String decodedAuth = "";
            String[] authParts = authorizationHeader.split("\\s+");
            String authInfo = authParts[1];
            byte[] bytes;
            bytes = Base64.getDecoder().decode(authInfo);
            decodedAuth = new String(bytes);

            String username = decodedAuth.split(":")[0];
            String password = decodedAuth.split(":")[1];

            User user = userService.findUserByUsername(username);

            if (!passwordEncoder.checkPassword(password, user.getPassword())) {
                throw new UnauthorizedException(NOT_AUTHORIZED);
            }

            return user;
        } catch (Exception e) {
            throw new UnauthorizedException(NOT_AUTHORIZED);
        }
    }

    @Override
    public void verifyUserHasAtLeastOneRoleByName(User user, List<String> roles) throws UnauthorizedException{
        List<String> userRoles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            userRoles.add(role.getRole());
        }
        if (Collections.disjoint(roles, userRoles)) {
            throw new UnauthorizedException(NOT_AUTHORIZED);
        }
    }
}
