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

    void changePassword(String oldPassword, String newPassword);

    boolean userExists(String username);
}
