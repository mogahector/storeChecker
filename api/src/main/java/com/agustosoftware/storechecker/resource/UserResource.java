package com.agustosoftware.storechecker.resource;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.domain.entity.UserList;
import com.agustosoftware.storechecker.exception.BadRequestException;
import com.agustosoftware.storechecker.exception.NotFoundException;
import com.agustosoftware.storechecker.repository.UserRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    public @ResponseBody
    ResponseEntity createUser (@RequestBody User user) {
        User createUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{userId}")
    public @ResponseBody
    ResponseEntity getUser(@PathVariable String userId) throws Exception {
        Long searchId = checkAndGetUserId(userId);

        User user = userRepository.findOne(searchId);
        if (user == null) {
            String errMsg = String.format("User with ID: %s was not found.", userId);
            throw new NotFoundException(errMsg);
        }

        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/{userId}")
    public @ResponseBody
    ResponseEntity updateUser(@PathVariable String userId, @RequestBody User user) throws Exception {
        Long searchId = checkAndGetUserId(userId);

        User existingUser = userRepository.findOne(searchId);
        if (existingUser == null) {
            String errMsg = String.format("User with ID: %s was not found.", userId);
            throw new NotFoundException(errMsg);
        }

        user.setId(existingUser.getId());
        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{userId}")
    public @ResponseBody
    ResponseEntity deleteUser(@PathVariable String userId) throws Exception {
        Long searchId = checkAndGetUserId(userId);

        userRepository.delete(searchId);

        return ResponseEntity.noContent().build();
    }   

    @GetMapping()
    public @ResponseBody ResponseEntity getUsers() {
        Iterable<User> users = userRepository.findAll();
        UserList userList = new UserList();
        userList.getUsers().addAll(Lists.newArrayList(users));
        return ResponseEntity.ok(userList);
    }

    private Long checkAndGetUserId(@PathVariable String userId) throws BadRequestException {
        Long searchId;
        try {
            searchId = Long.parseLong(userId);
        } catch (NumberFormatException e) {
            throw new BadRequestException("User id must be an integer value.");
        }
        return searchId;
    }

}
