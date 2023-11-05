package com.example.EmployeManagement.Utility;

import com.example.EmployeManagement.Entity.Employe;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class EmployeUtility {
private static String privatekey ="THIS_IS_PRIVATE";
public String generate(Employe employe){
    return Jwts.builder()
            .setId(UUID.randomUUID().toString())
            .setSubject("Login Authentication")
            .claim("name" ,employe.getName())
            .claim("email" , employe.getEmail())
            .claim("password" ,employe.getPassword())
            .setIssuedAt( new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 120 * 1000))
            .signWith(SignatureAlgorithm.HS512,privatekey)
            .compact();
}
    public void verify(String Authorization) {
        try {
            Jwts.parser().setSigningKey(privatekey).parseClaimsJws(Authorization);
        } catch (Exception e) {
            throw new RuntimeException("Token verification failed: " + e.getMessage());
        }
    }
}
