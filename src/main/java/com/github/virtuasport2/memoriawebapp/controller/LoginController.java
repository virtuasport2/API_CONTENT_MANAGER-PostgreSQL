package com.github.virtuasport2.memoriawebapp.controller;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.virtuasport2.memoriawebapp.dto.AuthRequest;
import com.github.virtuasport2.memoriawebapp.dto.AuthResponse;
import com.github.virtuasport2.memoriawebapp.dto.UtenteRequest;
import com.github.virtuasport2.memoriawebapp.model.PasswordResetToken;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.security.CustomUserDetailsService;
import com.github.virtuasport2.memoriawebapp.security.JwtUtil;
import com.github.virtuasport2.memoriawebapp.security.TokenBlacklist;
import com.github.virtuasport2.memoriawebapp.service.JwtService;
import com.github.virtuasport2.memoriawebapp.service.PasswordResetService;
import com.github.virtuasport2.memoriawebapp.service.UtenteService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordResetService passwordResetService;

    private final CustomUserDetailsService userDetailsService;

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Utente> getUser(@PathVariable String username) {
        Utente utente = utenteService.getUserByUsername(username);
        return ResponseEntity.ok(utente);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) throws NoSuchAlgorithmException {

        log.info("LOGIN START - email={}", request.getEmail());
        log.info("VERIFY USER");

        System.out.println("utenteService: " + utenteService);

        boolean isValid;
        try {
            isValid = utenteService.verifyUser(request.getEmail(), request.getPassword());
        } catch (Exception e) {
            log.warn("LOGIN FAILED - email={}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Login error\"}");
        }


        // Utente utente = utenteService.verifyUser1(request.getUsername(),
        // request.getPassword());
        if (!isValid) {
            log.warn("LOGIN FAILED - email={}", request.getEmail());
            // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenziali non
            // valide");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Invalid credentials\"}");
        } else {

            // Recupero dettagli utente
            UserDetails userDetails = userDetailsService.loadUserByEmail(request.getEmail());

            // Generazione del token JWT
            String token = JwtUtil.generateToken(userDetails);

            ResponseCookie jwtCookie = jwtService.createJwtCookie(token);

            // Risposta con il token
            // return ResponseEntity.ok(new AuthResponse(token));

            // Endpoint per autenticare l'utente e restituire il token nel cookie
            // return ResponseEntity.ok()
            // .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            // .body("Login successful");

            // return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");

            log.info("JWT GENERATED");
            // Crea un oggetto di risposta che contenga il token
            AuthResponse response = new AuthResponse(token);

            // Restituisci la risposta con il token JWT
            return ResponseEntity.ok().body(response);
        }

        // Autenticazione dell'utente
        // authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(request.getUsername(),
        // request.getPassword())
        // );

        // se asterisco questo controllo mi rewstituisce il token!!
        // Autenticazione dell'utente
        // Authentication authentication = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(request.getUsername(),
        // request.getPassword())
        // );

    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity<Utente> getUserByEmail(@PathVariable String email) {
        Utente utente = utenteService.getUserByEmail(email);
        return ResponseEntity.ok(utente);
    }

    @PostMapping("/test-logout")
    public ResponseEntity<Void> testLogout() {
        System.out.println("Test logout raggiunto");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test-logout1")
    public ResponseEntity<String> testLogout1(HttpServletRequest request, HttpServletResponse response) {
        // Estrai il token dall'header Authorization
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Rimuovi "Bearer " per ottenere solo il token JWT
            System.out.println("Token trovato per il logout: " + token);

            // Aggiungi il token alla blacklist per impedirne il riutilizzo
            TokenBlacklist.addToBlacklist(token);
            System.out.println("Token invalidato e aggiunto alla blacklist.");
        } else {
            System.out.println("Nessun token trovato nell'header Authorization.");
        }

        // Invalida la sessione utente
        request.getSession().invalidate();
        System.out.println("Sessione invalidata.");

        // Rimuovi il cookie JSESSIONID
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Usa true se il sito è HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // Cancella subito il cookie
        response.addCookie(cookie);
        System.out.println("Cookie JSESSIONID eliminato.");

        // Rimuovi il token dall'header di risposta (per i client che lo leggono)
        response.setHeader("Authorization", "");

        // Restituisci una conferma del logout
        return ResponseEntity.ok("Logout eseguito con successo");
    }

    // Crea un endpoint in un controller per invalidare la sessione e rimuovere il
    // cookie
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            // Logica per invalidare il token o sessione
            System.out.println("Token trovato: " + token);
            // Logica per rimuovere il token (ad esempio, revoca o eliminazione della
            // sessione)
        } else {
            System.out.println("Nessun token trovato");
        }

        // Invalida la sessione
        request.getSession().invalidate();

        // Rimuove il cookie di autenticazione
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // Se usi HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0); // Elimina il cookie
        response.addCookie(cookie);

        // Rimuove il token JWT dall'header (se usi JWT)
        response.setHeader("Authorization", ""); // Cancella il token dal client

        // Opzionale: Reindirizza alla homepage dopo il logout
        return ResponseEntity.status(HttpStatus.FOUND)
                .header(HttpHeaders.LOCATION, "/")
                .build();
    }

    // Nota. se uso un tipo string come corpo del messaggio :
    // allora uso POST /forgot-password?email=anna@email.com
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody UtenteRequest request, HttpServletRequest httpRequest) {
        String email = request.getEmail();
        passwordResetService.generatePasswordResetToken(email, httpRequest);

        return ResponseEntity.ok("Reset link sent if email exists.");
    }

}
