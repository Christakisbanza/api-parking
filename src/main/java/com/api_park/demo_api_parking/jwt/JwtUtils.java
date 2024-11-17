package com.api_park.demo_api_parking.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "Bearer ";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";
    public static final long EXPIRE_DAYS = 0;
    public static final long EXPIRE_HOURS = 0;
    public static final long EXPIRE_MINUTES = 30;

    private JwtUtils(){
    }

    private static Key generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start){
        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken(String name, String role){
        Date issuedAt = new Date();
        Date limit = toExpireDate(issuedAt);
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(name)
                .setIssuedAt(issuedAt)
                .setExpiration(limit)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .claim("role", role)
                .compact();

        return new JwtToken(token);
    }

    private static Claims getClaimsFromToken(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(generateKey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        }catch (JwtException e){
            log.error(String.format("Token Inválido %s", e.getMessage()));
        }
        return null;
    }

    public static String getUserNameFromToken(String token){
        return getClaimsFromToken(String.valueOf(token)).getSubject();
    }

    public static boolean isTokenValid (String token){
        try {
            Jwts.parser()
                .setSigningKey(generateKey()).build()
                .parseClaimsJws(refactorToken(token));
            return true;
        }catch (JwtException e){
            log.error(String.format("Token Inválido %s", e.getMessage()));
        }
        return false;
    }

    private static String refactorToken (String token){
        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

}
