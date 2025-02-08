package com.roshan.filedigitizationsystem.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String generateAccessToken(String token);
    String generateRefreshToken(String token);
    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
}
