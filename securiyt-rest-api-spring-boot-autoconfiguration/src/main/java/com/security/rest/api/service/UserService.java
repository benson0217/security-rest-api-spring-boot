package com.security.rest.api.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * user service , base on spinrg security JdbcUserDetailsManager 
 * @author benson0217
 *
 */
public interface UserService {

    void createUser(UserDetails user);

    void updateUser(UserDetails user);

    void deleteUser(String username);

    boolean userExists(String username);
}
