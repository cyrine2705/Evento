package com.app.evento.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JwtService { //mel best practice service naamalou interface o ba3ed impl
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractEmail(String token);
    String generateToken(Map<String, Object> extraClaims, String username);

}
