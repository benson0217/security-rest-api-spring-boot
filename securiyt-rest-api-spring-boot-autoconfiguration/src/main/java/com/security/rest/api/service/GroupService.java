package com.security.rest.api.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * group service , base on spinrg security JdbcUserDetailsManager 
 * @author benson0217
 *
 */
public interface GroupService {

    List<String> findAllGroups();

    List<String> findUsersInGroup(String groupName);

    void createGroup(String groupName, List<GrantedAuthority> authorities);

    void deleteGroup(String groupName);

    void renameGroup(String oldName, String newName);

    void addUserToGroup(String username, String group);

    void removeUserFromGroup(String username, String groupName);

    List<GrantedAuthority> findGroupAuthorities(String groupName);

    void addGroupAuthority(String groupName, GrantedAuthority authority);

    void removeGroupAuthority(String groupName, GrantedAuthority authority);
}
