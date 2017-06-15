package com.security.rest.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.upperCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.rest.api.model.Group;
import com.security.rest.api.model.GroupAuthority;
import com.security.rest.api.model.User;
import com.security.rest.api.service.GroupService;

import lombok.extern.slf4j.Slf4j;

/**
 * rest api for spring security group related operation 
 * @author benson0217
 *
 */
@Slf4j
@PreAuthorize("hasRole('ROLE_ADMIN')") 
@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired GroupService groupService;

    /**
     * find all group name
     * @return HttpEntity
     */
    @GetMapping
    public HttpEntity<List<String>> findAllGroups() {
        return new ResponseEntity<List<String>>(groupService.findAllGroups(), HttpStatus.OK);
    }

    /**
     * create group and group authority
     * @param group
     * @return HttpEntity
     */
    @PostMapping
    public HttpEntity<Object> createGroup(@RequestBody Group group) {

        List<GrantedAuthority> grantedAuthoriies = group.getGroupAuthoritise().stream()
                .map(authority -> new SimpleGrantedAuthority(upperCase(authority.getAuthority())))
                .collect(Collectors.toList());
        groupService.createGroup(group.getGroupName(), grantedAuthoriies);

        return new ResponseEntity<Object>(HttpStatus.CREATED);
    }

    /**
     * delete group by group name
     * @param groupName
     * @return HttpEntity
     */
    @DeleteMapping(path = "/{groupName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> deleteGroup(@PathVariable String groupName) {

        groupService.deleteGroup(groupName);

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * 
     * @param oldName - group original name
     * @param group - just need group name
     * @return HttpEntity
     */
    @PatchMapping(path = "/{oldName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> renameGroup(@PathVariable String oldName, @RequestBody Group group) {

        groupService.renameGroup(oldName, group.getGroupName());

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * find all the users for the group
     * @param groupName
     * @return HttpEntity - all users name in reponse body
     */
    @GetMapping(path = "/{groupName}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<List<String>> findUsersInGroup(@PathVariable String groupName) {
        return new ResponseEntity<List<String>>(groupService.findUsersInGroup(groupName), HttpStatus.OK);
    }

    /**
     * add user to group by group name
     * @param groupName 
     * @param user
     * @return HttpEntity
     */
    @PatchMapping(path = "/{groupName}/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> addUserToGroup(@PathVariable String groupName, @RequestBody User user) {

        groupService.addUserToGroup(user.getUsername(), groupName);

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * delete single user frome group
     * @param groupName
     * @param username
     * @return
     */
    @DeleteMapping(path = "/{groupName}/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> removeUserFromGroup(@PathVariable String groupName, @PathVariable String username) {

        groupService.removeUserFromGroup(username, groupName);

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * find groups authorities by group name
     * @param groupName
     * @return HttpEntity - all grantedAuthority in response body
     */
    @GetMapping(path = "/{groupName}/authorities", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<List<GrantedAuthority>> findGroupAuthorities(@PathVariable String groupName) {
        return new ResponseEntity<List<GrantedAuthority>>(groupService.findGroupAuthorities(groupName), HttpStatus.OK);
    }

    /**
     * delete single authority frome group
     * @param groupName
     * @param authority
     * @return HttpEntity
     */
    @DeleteMapping(path = "/{groupName}/authorities", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> removeGroupAuthority(@PathVariable String groupName, @RequestBody GroupAuthority authority) {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(upperCase(authority.getAuthority()));
        groupService.removeGroupAuthority(groupName, simpleGrantedAuthority);

        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }

    /**
     * add single authority to group
     * @param groupName
     * @param authority 
     * @return HttpEntity
     */
    @PatchMapping(path = "/{groupName}/authorities", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Object> addGroupAuthority(@PathVariable String groupName, @RequestBody GroupAuthority authority) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(upperCase(authority.getAuthority()));
        groupService.addGroupAuthority(groupName, simpleGrantedAuthority);
        return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    }
}
