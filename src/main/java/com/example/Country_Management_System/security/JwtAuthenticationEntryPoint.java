package com.example.Country_Management_System.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Component class for handling unauthorized access in JWT authentication.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Commences an authentication entry point, sending an "Access Denied" response with a detailed message.
     *
     * @param request       The HttpServletRequest that resulted in an AuthenticationException.
     * @param response      The HttpServletResponse to which the AuthenticationException message will be written.
     * @param authException The AuthenticationException that caused the invocation.
     * @throws IOException      If an input or output exception occurs.
     * @throws ServletException If a servlet-related exception occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Set the HTTP status code to SC_UNAUTHORIZED (401)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Get the PrintWriter to write the response
        PrintWriter writer = response.getWriter();

        // Write the "Access Denied" message along with the details of the AuthenticationException
        writer.println("Access Denied !! " + authException.getMessage());
    }
}