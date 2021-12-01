package io.joework.pictureproviderapi.util;

import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Base64;

import javax.crypto.SecretKey;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import io.joework.pictureproviderapi.domain.User;

public class JwtUtils {
 
    public static String generateToken(User user){
        return Jwts.builder()
            .setSubject(String.format("%s, %s, %s", user.getId(),user.getUsername(), user.getRole().name()))
            .setIssuedAt(Date.valueOf(LocalDate.now()))
            .setIssuer(JwtConfig.jwtIssuer)
            .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // one week
            .signWith(JwtConfig.key(), JwtConfig.SIGNATURE_ALGORITHM)
            .compact();            
    }


    public static Long getUserId(String token){
        return  Long.valueOf(builderMethod(JwtConfig.key(), token,0));
    }


    public static Date getExpirationDate(String token){
        return new Date(Jwts.parserBuilder()
                    .setSigningKey(JwtConfig.key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody().getExpiration().getTime());
    }


    public static String getRole(String token){
        return builderMethod(JwtConfig.key(), token,2);
    }

    private static String builderMethod(Key key, String token, int index){
        return Jwts.parserBuilder()
                    .setSigningKey(JwtConfig.SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject().split(",")[index].trim();
    }

    public static String getUsername(String token) { 
        return builderMethod(JwtConfig.key(), token,1);
    }


    public boolean validate(String token){
        try{
            Jwts.parserBuilder().setSigningKey(JwtConfig.key()).build().parseClaimsJws(token);
            return true;
        }catch(SignatureException e){
            System.out.println("Invalid JWT Signature " + e.getMessage());
        }catch(MalformedJwtException e){
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
        private static final String SECRET = "5s2BCxpNxdI58mAaAllBr/psyu91aCusvXy+kew9ytxQ/zhRtvcZMxVAjmkq8pVkSMA81+9Y0D4W06qGre+hYg==";
        private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

        static SecretKey key() {
            var res =  Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));
            System.out.println("res after decoding the secret = " + res.getEncoded());
            return res; 
        }
    }
}
