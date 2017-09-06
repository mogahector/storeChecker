package com.agustosoftware.storechecker.service;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.domain.entity.UserList;
import com.agustosoftware.storechecker.exception.BadRequestException;

public interface UserService {
    public User getUserById(String userId) throws Exception;
    public User getUserByUsername(String username) throws Exception;
    public User createUser(User caller, User user) throws Exception;
    public User updateUser(User caller, String userId, User user) throws Exception;
    public void deleteUser(User caller, String userId) throws Exception;
    public UserList listUsers() throws Exception;
    public int checkAndGetUserId(String id) throws BadRequestException;
}
