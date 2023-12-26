package com.example.Country_Management_System.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configuration class for defining security-related beans in the Spring application.
 */
@Configuration
public class AppConfig {

    /**
     * Defines a UserDetailsService bean to provide user details for authentication.
     * @return UserDetailsService implementation with in-memory user details.
     */
    @Bean
    public UserDetailsService userDetailsService(){

        // Define user details for an 'ADMIN' role user (Anam)
        UserDetails userDetails1 = User.builder().username("Anam")
                .password(passwordEncoder().encode("Anam123")).roles("ADMIN")
                .build();

        // Define user details for another 'ADMIN' role user (User)
        UserDetails userDetails2 = User.builder().username("User")
                .password(passwordEncoder().encode("Pass123")).roles("ADMIN")
                .build();

        // Create and return an InMemoryUserDetailsManager with the defined user details
        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    /**
     * Defines a PasswordEncoder bean for encoding and decoding passwords.
     *
     * @return BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines an AuthenticationManager bean for managing authentication within the application.
     *
     * @param builder AuthenticationConfiguration instance for building the AuthenticationManager.
     * @return AuthenticationManager instance.
     * @throws Exception If an error occurs during the configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}