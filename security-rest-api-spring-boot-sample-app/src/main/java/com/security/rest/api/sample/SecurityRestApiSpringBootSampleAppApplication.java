package com.security.rest.api.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.security.rest.api.autoconfiguration.EnableSecurityRestApi;

@SpringBootApplication
@EnableSecurityRestApi
public class SecurityRestApiSpringBootSampleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityRestApiSpringBootSampleAppApplication.class, args);
    }
    
}
