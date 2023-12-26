package com.example.Country_Management_System.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;



/**
 * Component class for filtering and processing JWT authentication in the Spring application.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * Filters and processes JWT authentication for each HTTP request.
     *
     * @param request     The HttpServletRequest to be processed.
     * @param response    The HttpServletResponse to be modified.
     * @param filterChain The FilterChain for continuing the filter chain.
     * @throws ServletException If a servlet-related exception occurs.
     * @throws IOException      If an input or output exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // Get the Authorization header from the request
        String requestHeader = request.getHeader("Authorization");
        logger.info("Header: {}", requestHeader);

        String username = null;
        String token = null;

        // Check if the Authorization header is present and starts with "Bearer"
        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
            // Extract the token from the header
            token = requestHeader.substring(7);

            try {
                // Extract the username from the token using JwtHelper
                username = this.jwtHelper.getUsernameFromToken(token);

            } catch (IllegalArgumentException e) {
                logger.info("Illegal Argument while fetching the username!!");
                e.printStackTrace();
            } catch (ExpiredJwtException e) {
                logger.info("Given jwt token is expired!!");
                e.printStackTrace();
            } catch (MalformedJwtException e) {
                logger.info("Some changes have been made in the token!! Invalid Token");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.info("Invalid Header Value!!");
        }

        // If the username is not null and no authentication is present in the SecurityContextHolder
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            try {
                // Fetch user details from the UserDetailsService based on the username
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Validate the token against user details
                Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);

                if (validateToken) {
                    // Set the authentication in the SecurityContextHolder
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.info("Validation fails!!");
                }

            } catch (UsernameNotFoundException e) {
                logger.info("User not found with username: {}", username);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}