package com.security.rest.api.handler.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.security.rest.api.controller.TestBasicAuth;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@Sql({"/groupBaseSql.sql", "/testInitData.sql"})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionHandlerIntegrationTest implements TestBasicAuth {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup(){
        restTemplate = restTemplate.withBasicAuth(ADEMIN_USERNAME, ADEMIN_PASSWORD);
    }

    @Test
    public void handleNoHandlerFound(){
        log.info(" run handleNoHandlerFound test ");

        ResponseEntity<SecurityApiDefaultErrorMessage> apiError = restTemplate.getForEntity("/handlerNotFound", SecurityApiDefaultErrorMessage.class);

        assertThat(apiError.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(apiError.getBody().getErrors().size()).isGreaterThan(0);
        assertThat(apiError.getBody().getMessage()).contains("No handler found");
    };

//    public void handleDataAccess(){}
    
    @Test
    public void handleAccessDenied(){
        log.info(" run handleAccessDenied test ");

        restTemplate = restTemplate.withBasicAuth(NUSER_USERNAME, NUSER_PASSWORD);
        ResponseEntity<SecurityApiDefaultErrorMessage> apiError = restTemplate.getForEntity("/groups", SecurityApiDefaultErrorMessage.class);

        assertThat(apiError.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(apiError.getBody().getErrors().size()).isGreaterThan(0);
        assertThat(apiError.getBody().getMessage()).contains("Access is denied");
    };

    @Test
    public void handleHttpRequestMethodNotSupported(){
        log.info(" run handleHttpRequestMethodNotSupported test ");

        ResponseEntity<SecurityApiDefaultErrorMessage> apiError = restTemplate.getForEntity("/groups/methodNotSupport", SecurityApiDefaultErrorMessage.class);
        
        assertThat(apiError.getStatusCode()).isEqualTo(HttpStatus.METHOD_NOT_ALLOWED);
        assertThat(apiError.getBody().getErrors().size()).isGreaterThan(0);
        assertThat(apiError.getBody().getMessage()).contains("Request method");
    }
    
    @Test
    public void handleBadCredentials(){
        log.info(" run handleBadCredentials test ");
        
        restTemplate = restTemplate.withBasicAuth(StringUtils.SPACE, StringUtils.SPACE);
        ResponseEntity<SecurityApiDefaultErrorMessage> apiError = restTemplate.getForEntity("/groups", SecurityApiDefaultErrorMessage.class);
        
        assertThat(apiError.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(apiError.getBody().getErrors().size()).isGreaterThan(0);
        assertThat(apiError.getBody().getMessage()).contains("Bad credentials");
    }
}
