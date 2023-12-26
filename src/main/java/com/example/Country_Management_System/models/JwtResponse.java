package com.example.Country_Management_System.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * Represent the response received from the server after authentication with token.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {

    String jwtToken;

    String username;
}
