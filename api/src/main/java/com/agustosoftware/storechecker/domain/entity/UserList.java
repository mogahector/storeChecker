package com.agustosoftware.storechecker.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserList {

    @JsonProperty("users")
    List<User> users;

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<User>();
        }
        return users;
    }
}
