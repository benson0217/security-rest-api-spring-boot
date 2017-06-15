package com.security.rest.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.security.rest.api.autoconfiguration.EnableSecurityRestApi;

@SpringBootApplication
@EnableSecurityRestApi
public class SecurityApiSpringBootAutoconfigureApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApiSpringBootAutoconfigureApplication.class, args);
    }
    
}
