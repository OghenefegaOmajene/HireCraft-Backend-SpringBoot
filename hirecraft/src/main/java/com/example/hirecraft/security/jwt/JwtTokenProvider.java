////package com.example.hirecraft.security.jwt;
////
////import io.jsonwebtoken.Claims;
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////import jakarta.annotation.PostConstruct;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.userdetails.UserDetails;
////import org.springframework.stereotype.Component;
////
////import java.util.Base64;
////import java.util.Date;
////
////@Component
////public class JwtTokenProvider {
////
////
////    @Value("${app.jwt-secret}")
////    private String secretKey;
////
////    @Value("${app.jwt-expiration-milliseconds}")
////    private long validityInMilliseconds;
////
////    /**
////     * Encode the secret key after construction.
////     */
////    @PostConstruct
////    protected void init() {
////        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
////    }
////
////    /**
////     * Generate a JWT for the given authentication.
////     * @param auth Authentication object containing principal
////     * @return the JWT string
////     */
////    public String generateToken(Authentication auth) {
////        UserDetails userDetails = (UserDetails) auth.getPrincipal();
////        Date now = new Date();
////        Date expiry = new Date(now.getTime() + validityInMilliseconds);
////
////        return Jwts.builder()
////                .setSubject(userDetails.getUsername())
////                .setIssuedAt(now)
////                .setExpiration(expiry)
////                .signWith(SignatureAlgorithm.HS512, secretKey)
////                .compact();
////    }
////
////    /**
////     * Validate the given JWT.
////     * @param token JWT string
////     * @return true if valid, false otherwise
////     */
////    public boolean validateToken(String token) {
////        try {
////            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
////            return true;
////        } catch (Exception e) {
////            return false;
////        }
////    }
////
////    /**
////     * Extract username (subject) from JWT.
////     * @param token JWT string
////     * @return username
////     */
////    public String getUsername(String token) {
////        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
////        return claims.getSubject();
////    }
////
////    /**
////     * @return token validity in milliseconds
////     */
////    public long getValidityInMilliseconds() {
////        return validityInMilliseconds;
////    }
////}
//
//
//package com.example.hirecraft.security.jwt;
//
//import io.jsonwebtoken.*;
//import jakarta.annotation.PostConstruct;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Base64;
//import java.util.Date;
//
//@Component
//public class JwtTokenProvider {
//    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
//
//    @Value("${app.jwt-secret}")
//    private String jwtSecret;
//
//    @Value("${app.jwt-expiration-milliseconds}")
//    private int jwtExpirationMs;
//
//    @PostConstruct
//    protected void init() {
//        // Ensure the secret is properly encoded
//        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
//    }
//
//    public String generateToken(Authentication authentication) {
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSecret)
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(jwtSecret)
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (SignatureException e) {
//            logger.error("Invalid JWT signature: {}", e.getMessage());
//        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
//        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
//        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
//        }
//        return false;
//    }
//
//    public String getUsernameFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(jwtSecret)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public long getJwtExpirationInMs() {
//        return jwtExpirationMs;
//    }
//}


package com.example.hirecraft.security.jwt;

import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationMs;

    @PostConstruct
    protected void init() {
        // Ensure the secret is properly encoded
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public long getJwtExpirationInMs() {
        return jwtExpirationMs;
    }
}