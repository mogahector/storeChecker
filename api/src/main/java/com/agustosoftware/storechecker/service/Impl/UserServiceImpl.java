package com.agustosoftware.storechecker.service.Impl;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.domain.entity.UserList;
import com.agustosoftware.storechecker.exception.BadRequestException;
import com.agustosoftware.storechecker.exception.NotFoundException;
import com.agustosoftware.storechecker.repository.UserRepository;
import com.agustosoftware.storechecker.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserById(String userId) throws Exception {
        int searchId = checkAndGetUserId(userId);
        User user = userRepository.findOne(searchId);

        if (user == null) {
            String errMsg = String.format("User with ID: %s was not found.", userId);
            throw new NotFoundException(errMsg);
        }

        return user;
    }

    @Override
    public User findUserByUsername(String username) throws Exception {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            String errMsg = String.format("User with username: %s was not found.", username);
            throw new NotFoundException(errMsg);
        }

        return user;
    }

    @Override
    public User createUser(User user) throws Exception {
        assertRequiredStringAttribute("username", user.getUsername());
        assertRequiredStringAttribute("email", user.getEmail());
        if (user.getPassword() != null) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String userId, User user) throws Exception {
        int searchId = checkAndGetUserId(userId);
        User existingUser = userRepository.findOne(searchId);

        if (existingUser == null) {
            String errMsg = String.format("User with ID: %s was not found.", userId);
            throw new NotFoundException(errMsg);
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
    public void deleteUser(String userId) throws Exception {
        int searchId = checkAndGetUserId(userId);
        userRepository.delete(searchId);
    }

    @Override
    public UserList listUsers() throws Exception {
        Iterable<User> users = userRepository.findAll();
        UserList userList = new UserList();
        userList.getUsers().addAll(Lists.newArrayList(users));
        return userList;
    }

    private void assertRequiredStringAttribute(String attributeName, String value) throws BadRequestException {
        if (StringUtils.isEmpty(value)) {
            throw new BadRequestException(String.format("%s cannot be empty.", attributeName));
        }
    }

    private int checkAndGetUserId(String userId) throws BadRequestException {
        int searchId;
        try {
            searchId = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id must be an integer value.");
        }
        return searchId;
    }
}
