package com.security.rest.api.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.rest.api.model.User;
import com.security.rest.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * rest api for spring security user related operation 
 * @author benson0217
 *
 */
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')") 
@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired UserService userService;

    /**
     * create user
     * @param user
     * @return HttpEntity
     */
    @PostMapping
    public HttpEntity<Object> createUser(@RequestBody User user) {
        
        UserDetails UserDetails = 
                new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<GrantedAuthority>());
        userService.createUser(UserDetails);
        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    /**
     * upate user name,password and change enable states
     * @param user
     * @return HttpEntity
     */
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

    /**
     * delete user by use name
     * @param username
     * @return HttpEntity
     */
    @DeleteMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * check user exists by user name
     * @param username
     * @return HttpEntity
     */
    @GetMapping(path = "/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> userExists(@PathVariable String username) {
        
        if(userService.userExists(username))
            return new ResponseEntity<Object>(HttpStatus.OK);
        else
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);   
    }
}
