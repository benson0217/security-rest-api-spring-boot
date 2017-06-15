package com.security.rest.api.autoconfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.security.rest.api.*" })
//@ConditionalOnWebApplication
//@EnableConfigurationProperties(SampleProperties.class)
public class SecurityRestApiAutoconfigure {
    
}
