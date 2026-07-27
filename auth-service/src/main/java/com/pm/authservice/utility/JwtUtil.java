package com.pm.authservice.utility;

import com.pm.authservice.model.Role;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import io.jsonwebtoken.Claims;

@Component
public class JwtUtil {

        private final Key secretKey;

        public JwtUtil(@Value("${jwt.secret}") String secret){
            byte[] keyBytes = Base64.getDecoder().decode(secret.getBytes(
                    StandardCharsets.UTF_8
            ));
            this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        }

        //this is going to form the jwt token
        public String generateToken(String email, Role role) {
            return Jwts.builder()
                    .subject(email)
                    .claim("role", role.name())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                    .signWith(secretKey)
                    .compact();
        }

        public void validateToken(String token){

            try{
                Jwts.parser().verifyWith( (SecretKey) secretKey)
                        .build()
                        .parseSignedClaims(token);

            }catch (SignatureException e) {
                throw new JwtException("Invalid JWT signature");
            } catch (JwtException e) {

                throw new RuntimeException(e);

            }

        }
        private Claims getClaims(String token) {
            return Jwts.parser()
                    .verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        public String extractEmail(String token) {
            return getClaims(token).getSubject();
        }

        public String extractRole(String token) {
            return getClaims(token).get("role", String.class);
        }



}
