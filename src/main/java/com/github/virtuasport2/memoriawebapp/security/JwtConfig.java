package com.github.virtuasport2.memoriawebapp.security;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class JwtConfig {
    
    //private static final String SECRET_KEY = "supersecretkey12345678901234567890"; // Minimo 32 byte

    @Value("${jwt.secret}")
    private String secretKey;// Recupera la chiave segreta da application.properties
	
    // Creazione della chiave HMAC in modo sicuro
    public  Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

// SECRET_KEY deve avere almeno 32 caratteri per HS256 (più lungo è, meglio è).
// Base64.getDecoder().decode(SECRET_KEY) converte la chiave in un array di byte.
// Keys.hmacShaKeyFor(byte[]) genera una chiave sicura compatibile con JJWT.