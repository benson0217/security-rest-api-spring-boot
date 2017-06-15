package com.security.rest.api.handler.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * handle spring security authentication error
 * @author benson0217
 *
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final static String BASIC_AUTH = "Basic auth error";

    @Autowired private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {

        SecurityApiDefaultErrorMessage errorMessage = 
                new SecurityApiDefaultErrorMessage(
                        HttpStatus.UNAUTHORIZED, authenticationException.getLocalizedMessage(), BASIC_AUTH);

        ServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        outputMessage.setStatusCode(HttpStatus.UNAUTHORIZED);

        mappingJackson2HttpMessageConverter.write(errorMessage, null, outputMessage);
    }
}