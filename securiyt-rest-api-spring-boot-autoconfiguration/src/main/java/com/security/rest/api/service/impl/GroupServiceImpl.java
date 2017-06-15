package com.security.rest.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.rest.api.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
    
    @Autowired JdbcUserDetailsManager jdbcUserDetailsManager;

    @Override
    public List<String> findAllGroups() {
        return jdbcUserDetailsManager.findAllGroups();
    }

    @Override
    public List<String> findUsersInGroup(String groupName) {
        return jdbcUserDetailsManager.findUsersInGroup(groupName);
    }

    @Override
    @Transactional
    public void createGroup(String groupName,
            final List<GrantedAuthority> authorities) {
        jdbcUserDetailsManager.createGroup(groupName, authorities);
    }

    @Override
    @Transactional
    public void deleteGroup(String groupName) {
        jdbcUserDetailsManager.deleteGroup(groupName);
    }

    @Override
    @Transactional
    public void renameGroup(String oldName, String newName) {
        jdbcUserDetailsManager.renameGroup(oldName, newName);
    }

    @Override
    @Transactional
    public void addUserToGroup(String username, String groupName) {
        jdbcUserDetailsManager.addUserToGroup(username, groupName);
    }

    @Override
    @Transactional
    public void removeUserFromGroup(String username, String groupName) {
        jdbcUserDetailsManager.removeUserFromGroup(username, groupName);
    }

    @Override
    public List<GrantedAuthority> findGroupAuthorities(String groupName) {
        return jdbcUserDetailsManager.findGroupAuthorities(groupName);
    }

    @Override
    @Transactional
    public void removeGroupAuthority(String groupName, GrantedAuthority authority) {
        jdbcUserDetailsManager.removeGroupAuthority(groupName, authority);
    }

    @Override
    @Transactional
    public void addGroupAuthority(final String groupName, GrantedAuthority authority) {
        jdbcUserDetailsManager.addGroupAuthority(groupName, authority);
    }

}
