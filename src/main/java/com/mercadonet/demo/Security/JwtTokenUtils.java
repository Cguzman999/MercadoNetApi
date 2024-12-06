/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mercadonet.demo.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author HP
 */

@Component
public class JwtTokenUtils {
    
   private final Key Secret_key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long Expiration_time = 10800000; // corresponde a 3horas
    
    public String GenerateToken(String usuario){
        Date now = new Date();
        Date Expirydate = new Date(now.getTime() + Expiration_time);
        return Jwts.builder()
                .setSubject(usuario)
                .setIssuedAt(now)
                .setExpiration(Expirydate)
                .signWith(Secret_key)
                .compact();
    }
    
    public String ValidateToken(String Token){
        try {
            Jwts.parserBuilder()
                .setSigningKey(Secret_key)
                .build()
                .parseClaimsJws(Token);
            return "Valido";
        }catch (ExpiredJwtException e) {
            return "El token ha expirado";
        }catch(JwtException | IllegalArgumentException ex ){
            return "El token es invalido";
        }
    }
}