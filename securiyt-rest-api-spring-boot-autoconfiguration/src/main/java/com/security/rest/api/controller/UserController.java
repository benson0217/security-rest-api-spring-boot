package com.security.rest.api.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.rest.api.model.User;
import com.security.rest.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')") 
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired UserService userService;

    @PostMapping
    public HttpEntity<Object> createUser(@RequestBody User user) {
        
        UserDetails UserDetails = 
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<GrantedAuthority>());
        userService.createUser(UserDetails);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    @PutMapping
    public HttpEntity<Object> updateUser(@RequestBody User user) {
        
        boolean isEnable;
        if(user.getEnable() == 1)
            isEnable = true;
        else
            isEnable = false;
        
        UserDetails userDetails = 
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(), user.getPassword(), isEnable, true, true, true, new ArrayList<GrantedAuthority>()
                        );
        userService.updateUser(userDetails);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    
//    @PatchMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public HttpEntity<Object> changePassword(String oldPassword, @RequestBody UserDetails user) {
//        
//        userService.changePassword(oldPassword, user.getPassword());
//        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> userExists(@PathVariable String username) {
        
        if(userService.userExists(username))
            return new ResponseEntity<Object>(HttpStatus.OK);
        else
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);   
    }
}
