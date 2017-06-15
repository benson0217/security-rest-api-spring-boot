package com.security.rest.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.rest.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired JdbcUserDetailsManager jdbcUserDetailsManager;

    @Override
    @Transactional
    public void createUser(UserDetails user) {
        jdbcUserDetailsManager.createUser(user);
    }

    @Override
    @Transactional
    public void updateUser(UserDetails user) {
        jdbcUserDetailsManager.updateUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        jdbcUserDetailsManager.deleteUser(username);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        jdbcUserDetailsManager.changePassword(oldPassword, newPassword);
    }

    @Override
    public boolean userExists(String username) {
        return jdbcUserDetailsManager.userExists(username);
    }

}
