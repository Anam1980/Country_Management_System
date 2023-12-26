package com.example.Country_Management_System.config;

import com.example.Country_Management_System.security.JwtAuthenticationEntryPoint;
import com.example.Country_Management_System.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for defining security-related settings in the Spring application.
 */
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    /**
     * Configures the security filter chain for the application.
     *
     * @param http HttpSecurity instance to configure security settings.
     * @return SecurityFilterChain instance.
     * @throws Exception If an error occurs during the configuration.
     */
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Configure security settings
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests()
                .requestMatchers("/country/**").authenticated()
                .requestMatchers("/auth/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add custom JwtAuthenticationFilter before UsernamePasswordAuthenticationFilter
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the SecurityFilterChain
        return http.build();
    }
}