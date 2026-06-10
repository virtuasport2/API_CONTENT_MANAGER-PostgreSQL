package com.github.virtuasport2.memoriawebapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Chiave segreta codificata in Base64 (USA UNA CHIAVE SICURA IN PRODUZIONE)
	private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("ZHdvaXdqaGRvaXB3amVmd29waWpmb3B3aWpoZW9vcGg=".getBytes());

    // Genera un JWT con scadenza
    public static String generateToken(UserDetails userDetails) throws NoSuchAlgorithmException {
    	
   	
        return Jwts.builder()
                .setSubject(userDetails.getUsername())  // Usa il nome utente come subject
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // Scadenza 1 ora
                .signWith(SECRET_KEY) // Firma con la chiave segreta
                .compact();
    }

    // Estrae il nome utente dal token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Controlla se il token è valido
    public boolean validateToken(String token, UserDetails userDetails) {
        return (extractUsername(token).equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    // Controlla se il token è scaduto
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    // Estrae un claim generico dal token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Usa il nuovo metodo per impostare la chiave
                .build() // Costruisce il parser
                .parseClaimsJws(token) // Parsea il token JWT
                .getBody();

        return claimsResolver.apply(claims);
    }
}
