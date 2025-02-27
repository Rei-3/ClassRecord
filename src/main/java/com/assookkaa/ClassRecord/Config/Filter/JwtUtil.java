package com.assookkaa.ClassRecord.Config.Filter;

import com.assookkaa.ClassRecord.Entity.User;
import com.assookkaa.ClassRecord.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final UserRepository userRepository;
    private SecretKey secretKey;

    @Value("${Secret}")
    String secret;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void summonSecretKey() {
        // Ensure the secret is Base64 encoded and valid
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private final Integer expiration = 1000 * 60 * 60; // 1 hour
    private final Integer refreshExpiration = 1000 * 60 * 60 * 24 * 5;

    private final Integer classroom_key_expiration = 1000 * 60 * 60 * 24 * 7;

    public String generateAccessToken(String username, String otp, Integer userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("otp", otp)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateClassroomKeyToken(String subject, String classroomId){
        return Jwts.builder()
                .setSubject(subject)
                .claim("classroomId" , classroomId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + classroom_key_expiration))
                .signWith(secretKey)
                .compact();
    }

    public String regenerateClassroomKeyToken(String subject, String classroomId) {
        return Jwts.builder()
                .setSubject(subject)
                .claim("classroomId", classroomId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + classroom_key_expiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey)
                .compact();
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    public String getOtpFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("otp", String.class);
    }

    public Integer getUserIdFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.get("userId", Integer.class);
    }

    private boolean isTokenExpired(String token) {
        Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final String otp = getOtpFromToken(token);
        final Integer userId = getUserIdFromToken(token);

        User user = userRepository.findByUsername(username);

        Boolean isUsernameValid = username.equals(user.getUsername());
        Boolean isOtpValid = otp.equals(user.getOtp());
        Boolean isUserIdValid = userId.equals(user.getId());
        Boolean isTokenExpired = isTokenExpired(token);

        return isUsernameValid && isOtpValid && isUserIdValid && isTokenExpired;
    }
}
