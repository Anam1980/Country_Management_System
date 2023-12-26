package com.example.Country_Management_System.models;


import lombok.*;
import lombok.experimental.FieldDefaults;


/**
 * Represent the request from the client to the server for authentication.
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtRequest {

    String username;

    String password;



}
