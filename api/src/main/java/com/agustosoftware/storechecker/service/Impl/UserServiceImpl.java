package com.agustosoftware.storechecker.service.Impl;

import com.agustosoftware.storechecker.domain.entity.Role;
import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.domain.entity.UserList;
import com.agustosoftware.storechecker.exception.BadRequestException;
import com.agustosoftware.storechecker.exception.ForbiddenException;
import com.agustosoftware.storechecker.exception.NotFoundException;
import com.agustosoftware.storechecker.repository.RoleRepository;
import com.agustosoftware.storechecker.repository.UserRepository;
import com.agustosoftware.storechecker.security.PasswordEncoder;
import com.agustosoftware.storechecker.service.RoleEnum;
import com.agustosoftware.storechecker.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder password;

    private static final String INVALID_ROLE_ERROR_MESSAGE = String.format(
            "Users must have one and only one role, acceptable values are: %s", RoleEnum.getValues());

    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
    private static final String PASSWORD_PATTERN_ERROR_MESSAGE = "Password must be between 6 to 20 characters" +
            " string with at least one digit, one upper case letter, one lower case letter and one special symbol" +
            " (“@#$%”)";

    private static final String USERNAME_PATTERN = "^[A-Za-z0-9][a-zA-Z0-9-_.@]{6,20}";
    private static final String USERNAME_PATTERN_ERROR_MESSAGE = "Username must be between 6 to 20 characters," +
            " begin with an alphanumeric character, have no spaces, and only contain the following valid special" +
            " characters: - _ . @";

    @Override
    public User getUserById(String userId) throws Exception {
        int searchId = checkAndGetUserId(userId);
        User user = userRepository.findOne(searchId);

        if (user == null) {
            String errMsg = String.format("User with ID: %s was not found.", userId);
            throw new NotFoundException(errMsg);
        }

        return user;
    }

    @Override
    public User getUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            String errMsg = String.format("User with username: %s was not found.", username);
            throw new NotFoundException(errMsg);
        }

        return user;
    }

    @Override
    public User createUser(User caller, User user) throws Exception {
        validatePattern(USERNAME_PATTERN, user.getUsername(), USERNAME_PATTERN_ERROR_MESSAGE);
        validatePattern(PASSWORD_PATTERN, user.getPassword(), PASSWORD_PATTERN_ERROR_MESSAGE);

        assertRequiredStringAttribute("email", user.getEmail());

        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            String errMsg = String.format("User with username: %s already exists.", user.getUsername());
            throw new BadRequestException(errMsg);
        }

        if (user.getRoles().isEmpty() || user.getRoles().size() > 1) {
            throw new BadRequestException(INVALID_ROLE_ERROR_MESSAGE);
        }

        Role userRole = getRoleFromUser(user);
        assertRequiredStringAttribute("role", userRole.getRole());

        Role role = roleRepository.findByRole(userRole.getRole());
        if (role == null) {
            throw new BadRequestException(INVALID_ROLE_ERROR_MESSAGE);
        }

        compareRoleWeights(getRoleFromUser(caller), role);

        user.setRoles(Collections.singleton(role));

        user.setPassword(password.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }



    @Override
    public User updateUser(User caller, String userId, User user) throws Exception {
        int searchId = checkAndGetUserId(userId);
        User existingUser = userRepository.findOne(searchId);

        if (existingUser == null) {
            String errMsg = String.format("User with ID: %s was not found.", userId);
            throw new NotFoundException(errMsg);
        }

        if (caller.getId() != existingUser.getId()) {
            compareRoleWeights(getRoleFromUser(caller), getRoleFromUser(existingUser));
        }

        // Allowed attributes for update
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getLastName() != null) {
            existingUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        userRepository.save(existingUser);

        return existingUser;
    }

    @Override
    public void deleteUser(User caller, String userId) throws Exception {
        int searchId = checkAndGetUserId(userId);

        User user = userRepository.findOne(searchId);

        if (user == null) {
            return;
        }

        compareRoleWeights(getRoleFromUser(caller), getRoleFromUser(user));
        userRepository.delete(searchId);
    }

    @Override
    public UserList listUsers() throws Exception {
        Iterable<User> users = userRepository.findAll();
        UserList userList = new UserList();
        userList.getUsers().addAll(Lists.newArrayList(users));
        return userList;
    }

    private Role getRoleFromUser(User user) {
        Iterator roleIterator = user.getRoles().iterator();
        return (Role) roleIterator.next();
    }

    private void compareRoleWeights(Role callerRole, Role role) throws ForbiddenException {
        RoleEnum userRoleEnum = RoleEnum.fromName(role.getRole());
        RoleEnum callerRoleEnum = RoleEnum.fromName(callerRole.getRole());

        if (userRoleEnum == null || callerRoleEnum == null) {
            throw new IllegalStateException("Invalid state");
        }

        if (!callerHasLowerWeight(callerRoleEnum.getWeight(), userRoleEnum.getWeight())) {
            throw new ForbiddenException("Unable to create/update/delete user with higher permissions.");
        }
    }

    private boolean callerHasLowerWeight(int callerWeight, int roleWeight) {
        return callerWeight < roleWeight;
    }

    private void assertRequiredStringAttribute(String attributeName, String value) throws BadRequestException {
        if (StringUtils.isEmpty(value)) {
            throw new BadRequestException(String.format("%s cannot be empty.", attributeName));
        }
    }

    public int checkAndGetUserId(String userId) throws BadRequestException {
        int searchId;
        try {
            searchId = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id must be an integer value.");
        }
        return searchId;
    }

    private void validatePattern(String pattern, String value, String errMsg) throws BadRequestException {
        Pattern pat = null;
        try {
            pat = Pattern.compile(pattern);
        } catch (Exception ex){
            throw new IllegalStateException(String.format("'%s' is not a valid regular expression.", pattern));
        }
        if(value == null || !pat.matcher(value).matches()){
            throw new BadRequestException(errMsg);
        }
    }

}
