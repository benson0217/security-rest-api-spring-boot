package com.security.rest.api.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
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

import com.security.rest.api.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@Sql({"/groupBaseSql.sql", "/testInitData.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerIntegrationTest extends ControllerIntegtationTest implements TestBasicAuth {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){
        restTemplate = restTemplate.withBasicAuth(ADEMIN_USERNAME, ADEMIN_PASSWORD);
    }
    
    @After
    public void cleandata() {
        
    }

    public UserControllerIntegrationTest() {
        super(ADEMIN_USERNAME, ADEMIN_PASSWORD);
    }

    @Test
    public void createUser() {
        log.info(" run createUser test ");

        User user = new User();
        user.setEnable(1);
        user.setUsername("ben");
        user.setPassword("ben0217");
        ResponseEntity<Object> creatResult = restTemplate.postForEntity("/users", user, Object.class);

        assertThat(creatResult.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void updateUser() {

        log.info(" run updateUser test ");

        User user = new User();
        user.setEnable(1);
        user.setUsername("auser");
        user.setPassword("ben0217");
        final HttpEntity<User> requestEntity = new HttpEntity<>(user);
        ResponseEntity<Object> putResult  = restTemplate.exchange("/users", HttpMethod.PUT, requestEntity, Object.class);
        assertThat(putResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void deleteUser() {

        log.info(" run deleteUser test ");

        ResponseEntity<Object> deleteResult  = restTemplate.exchange("/users/auser", HttpMethod.DELETE, null, Object.class);
        assertThat(deleteResult.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);  
    }


    @Test
    public void userExists() {

        log.info(" run userExists test ");

        ResponseEntity<Object> getResult = restTemplate.getForEntity("/users/auser", Object.class);

        assertThat(getResult.getStatusCode()).isEqualTo(HttpStatus.OK);  

        getResult = restTemplate.getForEntity("/users/123", Object.class);

        assertThat(getResult.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);  

    }
}
