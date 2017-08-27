package com.agustosoftware.storechecker.service;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.domain.entity.UserList;

public interface UserService {
    public User findUserById(String userId) throws Exception;
    public User findUserByUsername(String username) throws Exception;
    public User createUser(User user) throws Exception;
    public User updateUser(String userId, User user) throws Exception;
    public void deleteUser(String userId) throws Exception;
    public UserList listUsers() throws Exception;
}
