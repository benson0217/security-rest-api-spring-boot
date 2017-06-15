package com.security.rest.api.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void createUser(UserDetails user);

    void updateUser(UserDetails user);

    void deleteUser(String username);

    void changePassword(String oldPassword, String newPassword);

    boolean userExists(String username);
}
