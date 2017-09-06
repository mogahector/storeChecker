package com.agustosoftware.storechecker.resource;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.service.AuthorizationService;
import com.agustosoftware.storechecker.service.RoleEnum;
import com.agustosoftware.storechecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity createUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @RequestBody User user) throws Exception {
        User caller = authorizationService.checkAndGetCallerFromAuthorizationHeader(authorizationHeader);
        authorizationService.verifyUserHasAtLeastOneRoleByName(caller, Arrays.asList(RoleEnum.ADMIN.getName(),
                                                                                     RoleEnum.OWNER.getName()));

        User createUser = service.createUser(caller, user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable String userId) throws Exception {
        User caller = authorizationService.checkAndGetCallerFromAuthorizationHeader(authorizationHeader);

        // User should be allowed to retrieve themselves
        if (caller.getId() != service.checkAndGetUserId(userId)) {
            authorizationService.verifyUserHasAtLeastOneRoleByName(caller, Arrays.asList(
                    RoleEnum.ADMIN.getName(),
                    RoleEnum.OWNER.getName()));
        }
        User user = service.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable String userId, @RequestBody User user) throws Exception {
        User caller = authorizationService.checkAndGetCallerFromAuthorizationHeader(authorizationHeader);

        // User should be allowed to update themselves
        if (caller.getId() != service.checkAndGetUserId(userId)) {
            authorizationService.verifyUserHasAtLeastOneRoleByName(caller, Arrays.asList(
                    RoleEnum.ADMIN.getName(),
                    RoleEnum.OWNER.getName()));
        }

        User updatedUser = service.updateUser(caller, userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(path = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable String userId) throws Exception {
        User caller = authorizationService.checkAndGetCallerFromAuthorizationHeader(authorizationHeader);
        authorizationService.verifyUserHasAtLeastOneRoleByName(caller, Arrays.asList(RoleEnum.ADMIN.getName(),
                                                                                     RoleEnum.OWNER.getName()));
        service.deleteUser(caller, userId);
        return ResponseEntity.noContent().build();
    }   

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity getUsers(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) throws Exception {
        User caller = authorizationService.checkAndGetCallerFromAuthorizationHeader(authorizationHeader);
        authorizationService.verifyUserHasAtLeastOneRoleByName(caller, Arrays.asList(RoleEnum.ADMIN.getName(),
                                                                                     RoleEnum.OWNER.getName()));
        return ResponseEntity.ok(service.listUsers());
    }



}
