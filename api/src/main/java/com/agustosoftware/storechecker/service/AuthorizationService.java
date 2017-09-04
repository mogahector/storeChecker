package com.agustosoftware.storechecker.service;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.exception.UnauthorizedException;

import java.util.List;

public interface AuthorizationService {

    User checkAndGetCallerFromAuthorizationHeader(String authorizationHeader) throws UnauthorizedException;
    void verifyUserHasAtLeastOneRoleByName(User user, List<String> roles) throws UnauthorizedException;
}
