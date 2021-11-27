package io.joework.pictureproviderapi.util;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.joework.pictureproviderapi.domain.User;

public class JwtUtils {
   

    public static String generateToken(User user){
        
        return Jwts.builder()
            .setSubject(String.format("%s, %s", user.getId(),user.getUsername()))
            .setIssuedAt(Date.valueOf(LocalDate.now()))
            .setIssuer(JwtConfig.jwtIssuer)
            .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // one week
            .signWith(JwtConfig.key)
            .compact();            
    }


    public static Long getUserId(String token){

        Claims claims = Jwts.parserBuilder()
                            .setSigningKey(JwtConfig.key)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();

        return Long.valueOf(claims.getSubject().split(",")[0]);
    }


    public static Date getExpirationDate(String token){
        return new Date(Jwts.parserBuilder()
                    .setSigningKey(JwtConfig.key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getExpiration().getTime());
    }


    public static String getUsername(String token) { 
        return Jwts.parserBuilder()   
                    .setSigningKey(JwtConfig.key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getSubject().split(",")[1].trim();
    }


    public boolean validate(String token){
        try{
            Jwts.parserBuilder().setSigningKey(JwtConfig.key).build().parseClaimsJws(token);
            return true;
        }catch(SignatureException e){
            System.out.println("Invalid JWT Signature " + e.getMessage());
        }
        
        catch(MalformedJwtException e){
            System.out.println("Invalid JWT signature - " + e.getMessage());
        }catch(ExpiredJwtException e){
            System.out.println("Expired JWT signature - " + e.getMessage());
        }catch(UnsupportedJwtException e){
            System.out.println("Unsupported JWT signature - " + e.getMessage());
        }catch (IllegalArgumentException e) {
        System.out.println("JWT claims string is empty - "+ e.getMessage());
        }
    
        return false; 
    }
    
    static class JwtConfig { 
        private static final String jwtIssuer = "io.joework";
        private static final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
        private static Key key =  Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
}
