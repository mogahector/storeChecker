package com.agustosoftware.storechecker.resource;

import com.agustosoftware.storechecker.domain.entity.User;
import com.agustosoftware.storechecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Controller
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    ResponseEntity createUser (@RequestBody User user) throws Exception {
        User createUser = service.createUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(createUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping(path = "/{userId}")
    public @ResponseBody
    ResponseEntity getUser(@PathVariable String userId) throws Exception {
        User user = service.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping(path = "/{userId}")
    public @ResponseBody
    ResponseEntity updateUser(@PathVariable String userId, @RequestBody User user) throws Exception {
        User updatedUser = service.updateUser(userId, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(path = "/{userId}")
    public @ResponseBody
    ResponseEntity deleteUser(@PathVariable String userId) throws Exception {
        service.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }   

    @GetMapping()
    public @ResponseBody ResponseEntity getUsers() throws Exception {
        return ResponseEntity.ok(service.listUsers());
    }



}
