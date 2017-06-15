package com.security.rest.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.security.rest.api.model.Group;
import com.security.rest.api.model.GroupAuthority;
import com.security.rest.api.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@Sql({"/groupBaseSql.sql", "/testInitData.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GroupControllerIntegrationTest extends ControllerIntegtationTest implements TestBasicAuth {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){
        restTemplate = restTemplate.withBasicAuth(ADEMIN_USERNAME, ADEMIN_PASSWORD);
    }

    public GroupControllerIntegrationTest() {
        super(ADEMIN_USERNAME, ADEMIN_PASSWORD);
    }

    @Test
    public void findAllGroups(){

        log.info(" run findAllGroups test ");

        ResponseEntity<String[]> groups = restTemplate.getForEntity("/groups", String[].class);

        assertThat(groups.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(groups.getBody().length).isGreaterThan(0); 
    };

    @Test
    public void findUsersInGroup(){
        log.info(" run findUsersInGroup test ");

        ResponseEntity<String[]> users = restTemplate.getForEntity("/groups/Admins/users", String[].class);

        assertThat(users.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(users.getBody().length).isGreaterThan(0); 
        assertThat(users.getBody()[0]).isEqualTo("auser"); 
    };

    @Test
    public void createGroup(){
        log.info(" run createGroup test ");

        Group group = new Group();
        group.setGroupName("Test");
        List<GroupAuthority> groupAuthoritise = new ArrayList<GroupAuthority>();
        GroupAuthority groupAuthority = new GroupAuthority();
        groupAuthority.setAuthority("ROLE_TEST");
        groupAuthoritise.add(groupAuthority);
        group.setGroupAuthoritise(groupAuthoritise);
        ResponseEntity<Object> creatResult = restTemplate.postForEntity("/groups", group, Object.class);

        assertThat(creatResult.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    };

    @Test
    public void deleteGroup(){
        log.info(" run deleteGroup test ");

        ResponseEntity<Object> deleteResult  = restTemplate.exchange("/groups/User", HttpMethod.DELETE, null, Object.class);

        assertThat(deleteResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    };

    @Test
    public void renameGroup(){
        log.info(" run renameGroup test ");

        Group group = new Group();
        group.setGroupName("NEW_ADMIN");
        final HttpEntity<Group> requestEntity = new HttpEntity<>(group);
        ResponseEntity<Object> pathcResult  = restTemplate.exchange("/groups/Admin", HttpMethod.PATCH, requestEntity, Object.class);
        assertThat(pathcResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    };

    @Test
    public void addUserToGroup(){
        log.info(" run addUserToGroup test ");

        User user = new User();
        user.setUsername("nuser");
        final HttpEntity<User> requestEntity = new HttpEntity<>(user);
        ResponseEntity<Object> pathcResult  = restTemplate.exchange("/groups/Admins/users", HttpMethod.PATCH, requestEntity, Object.class);
        assertThat(pathcResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    };

    @Test
    public void removeUserFromGroup(){
        log.info(" run removeUserFromGroup test ");

        ResponseEntity<Object> deleteResult  = restTemplate.exchange("/groups/Admins/users/auser", HttpMethod.DELETE, null, Object.class);
        assertThat(deleteResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);  
    };

    @Test
    public void findGroupAuthoritiesTest() {
        log.info(" run findGroupAuthorities test ");

        ResponseEntity<String> authorities = restTemplate.getForEntity("/groups/Admins/authorities", String.class);

        assertThat(authorities.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authorities.getBody(), containsString( "ROLE_ADMIN" ));
        assertThat(authorities.getBody(), containsString( "ROLE_USER" ));
    }

    @Test
    public void addGroupAuthority(){
        log.info(" run addGroupAuthority test ");

        GroupAuthority authority = new GroupAuthority();
        authority.setAuthority("ROLE_TEST");
        final HttpEntity<GroupAuthority> requestEntity = new HttpEntity<>(authority);
        ResponseEntity<Object> pathcResult  = restTemplate.exchange("/groups/Admins/authorities", HttpMethod.PATCH, requestEntity, Object.class);
        assertThat(pathcResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    };

    @Test
    public void removeGroupAuthority(){
        log.info(" run removeGroupAuthority test ");

        GroupAuthority authority = new GroupAuthority();
        authority.setAuthority("ROLE_USER");
        final HttpEntity<GroupAuthority> requestEntity = new HttpEntity<>(authority);
        ResponseEntity<Object> deleteResult  = restTemplate.exchange("/groups/Admins/authorities", HttpMethod.DELETE, requestEntity, Object.class);
        assertThat(deleteResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    };
}

