package com.example.Country_Management_System.controller;

import com.example.Country_Management_System.models.JwtRequest;
import com.example.Country_Management_System.models.JwtResponse;
import com.example.Country_Management_System.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    // Logger for logging authentication-related actions
    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    /**
     * Handles the "/auth/login" endpoint for user authentication and token generation.
     *
     * @param request The JwtRequest containing the username and password.
     * @return ResponseEntity containing JwtResponse with the generated JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        // Authenticate user credentials
        this.doAuthenticate(request.getUsername(), request.getPassword());

        // Load user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // Generate JWT token through JwtHelper
        String token = this.jwtHelper.generateToken(userDetails);

        // Build JwtResponse
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();

        // Return ResponseEntity with JwtResponse and HTTP status OK
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Authenticates the user with the provided username and password using the AuthenticationManager.
     *
     * @param username The username to authenticate.
     * @param password The password to authenticate.
     */
    private void doAuthenticate(String username, String password) {
        // Create an authentication token with the provided username and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);

        // Authenticate with the AuthenticationManager
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            // Throw a BadCredentialsException if authentication fails
            throw new BadCredentialsException("Invalid Username or Password!!");
        }
    }

    /**
     * Exception handler for handling BadCredentialsException.
     *
     * @return Error message for invalid credentials.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid!!";
    }
}