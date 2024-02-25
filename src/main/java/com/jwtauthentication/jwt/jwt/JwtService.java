package com.jwtauthentication.jwt.jwt;

import java.sql.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(String userName);

    String extractUsername(String token);

    Date extractExpiration(String token);

    Boolean validateToken(String token, UserDetails userDetails);

    <T> T extractClaims(String token, Function<Claims, T> claimsResolver);
}
