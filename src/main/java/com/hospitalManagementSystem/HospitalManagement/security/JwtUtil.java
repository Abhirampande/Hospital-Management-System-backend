package com.hospitalManagementSystem.HospitalManagement.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class JwtUtil {
    private final String secretKey = "bfb76c0d96decf97f3ac8842913a4a869adbb81a411586905a010d775c045446";
    private final long expiration = 86400000;// 1day

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities()
                        .stream()
                        .map(Objects::toString)
                        .toList())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration
                ))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public List<String> extractRoles(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        return extractUsername(token).equals(userDetails.getUsername()) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                        .build().parseClaimsJws(token).getBody().getExpiration();
                return expiration.before(new Date());
    }


}
