package com.sdetcommerce.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey signingKey;
    private final long jwtExpirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.expiration-ms}") long jwtExpirationMs) {

        this.signingKey =
                Keys.hmacShaKeyFor(
                        jwtSecret.getBytes(StandardCharsets.UTF_8)
                );

        this.jwtExpirationMs = jwtExpirationMs;
    }

    /*
     * Generates JWT containing:
     *
     * subject = user email
     * userId  = database user ID
     * role    = ROLE_USER / ROLE_ADMIN
     */
    public String generateToken(
            String email,
            Long userId,
            String role) {

        Date issuedAt = new Date();

        Date expiration =
                new Date(
                        issuedAt.getTime()
                                + jwtExpirationMs
                );

        return Jwts.builder()
                .subject(email)
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    /*
     * Temporary compatibility method.
     *
     * Existing code still calling:
     *
     * generateToken(email, userId)
     *
     * will continue compiling until we update
     * all callers to explicitly provide the role.
     */
    public String generateToken(
            String email,
            Long userId) {

        return generateToken(
                email,
                userId,
                "ROLE_USER"
        );
    }

    /*
     * Extract email from JWT subject.
     */
    public String extractEmail(
            String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    /*
     * Extract user ID stored inside JWT.
     */
    public Long extractUserId(
            String token) {

        Number userId =
                extractAllClaims(token)
                        .get(
                                "userId",
                                Number.class
                        );

        return userId.longValue();
    }

    /*
     * Extract ROLE_USER / ROLE_ADMIN from JWT.
     */
    public String extractRole(
            String token) {

        return extractAllClaims(token)
                .get(
                        "role",
                        String.class
                );
    }

    /*
     * Check whether JWT is still valid.
     *
     * Signature is also validated while parsing token.
     */
    public boolean isTokenValid(
            String token) {

        try {

            Date expiration =
                    extractAllClaims(token)
                            .getExpiration();

            return expiration.after(
                    new Date()
            );

        } catch (Exception exception) {

            return false;
        }
    }

    /*
     * Parse and verify JWT.
     *
     * If JWT signature is invalid, expired,
     * malformed, etc., JJWT throws an exception.
     */
    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}