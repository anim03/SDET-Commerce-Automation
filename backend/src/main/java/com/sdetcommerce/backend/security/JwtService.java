package com.sdetcommerce.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import io.jsonwebtoken.Claims;

@Service
public class JwtService {

    private static final String SECRET =
            "sdet-commerce-super-secret-key-for-jwt-2026";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(
                SECRET.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateToken(
            Long userId,
            String email) {

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )
                .signWith(getSigningKey())
                .compact();
    }
    public Claims extractClaims(String token) {
    return Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
}

public String extractEmail(String token) {
    return extractClaims(token).getSubject();
}

public Long extractUserId(String token) {
    return extractClaims(token)
            .get("userId", Long.class);
}

public boolean isTokenValid(String token) {
    try {
        extractClaims(token);
        return true;
    } catch (Exception exception) {
        return false;
    }
}
}