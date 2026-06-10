package com.github.virtuasport2.memoriawebapp.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret}") // Segreto in Base64 nel file application.properties
    private String secretKey;

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 ore

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
 

    // **Genera un JWT senza claims aggiuntivi**
    public String generateToken(String username) {
        return generateToken(Map.of(), username);
    }

    // **Genera un JWT con claims personalizzati**
    public String generateToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .addClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey()) // Metodo aggiornato
                .compact();
    }

    // **Estrai il nome utente dal token**
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // **Verifica se il token è valido**
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }

    // **Estrai la data di scadenza**
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // **Controlla se il token è scaduto**
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // **Estrai un singolo claim generico**
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	final Claims claims = Jwts.parserBuilder()
    		    .setSigningKey(getSignKey()) // Imposta la chiave di firma
    		    .build()
    		    .parseClaimsJws(token) // Parsea il token
    		    .getBody();

        return claimsResolver.apply(claims);
    }
    
// Configurare il Cookie HttpOnly  - Creazione del cookie con il token JWT.
// Al momento del login, dopo aver generato il token JWT, memorizzalo in un cookie
    public ResponseCookie createJwtCookie(String jwt) {
        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(7))
                .sameSite("Strict")
                .build();
    } 
}