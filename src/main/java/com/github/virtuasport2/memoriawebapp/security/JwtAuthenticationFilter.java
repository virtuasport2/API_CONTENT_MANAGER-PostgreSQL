package com.github.virtuasport2.memoriawebapp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


	
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
    	System.out.println("JWT Auth nel SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());

    	logger.debug("Inizio doFilterInternal per URI: " + request.getRequestURI());
  
//        // Se la richiesta è per il logout, bypassa il filtro JWT e termina immediatamente
//        if (request.getRequestURI().equals("/api/auth/logout")) {
//            logger.debug("Richiesta di logout, bypassando il filtro JWT");
//            // Esegui le operazioni di logout, come la rimozione del cookie JWT
//            Cookie cookie = new Cookie("JWT", null);
//            cookie.setHttpOnly(true);
//            cookie.setSecure(true);  // Se HTTPS
//            cookie.setMaxAge(0);  // Scade subito
//            cookie.setPath("/");
//            response.addCookie(cookie);
//
//            // 2. Pulisci il SecurityContext
//            SecurityContextHolder.clearContext();  // Rimuove l'autenticazione dal contesto di sicurezza
//
//            // 3. Invalida la sessione HTTP
//            request.getSession().invalidate();  // Invalida la sessione HTTP, se esistente            
//            
//            // Imposta la risposta a OK (esito del logout)
//            response.setStatus(HttpServletResponse.SC_OK);
//            return;  // Non proseguire con il prossimo filtro (non passiamo al filtro JWT)
//        }        
        
        
        
        
        
        
        // Inizializza la variabile per il token JWT a null
        String token = null;
        
        // Prova a estrarre il token dai cookie presenti nella richiesta
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            // Scorre tutti i cookie per trovare quello con nome "jwt"
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    // Se trovato, assegna il valore del cookie alla variabile token
                    token = cookie.getValue();
                    break; // Esce dal ciclo una volta trovato il token
                }
            }
        }

        
        
  
        
        
        // Se il token non è stato trovato nei cookie, cerca nell'header Authorization
        if (token == null) {
            // Recupera il valore dell'header "Authorization"
            String authHeader = request.getHeader("Authorization");
            // Verifica che l'header non sia nullo e che inizi con "Bearer "
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                // Estrae il token rimuovendo il prefisso "Bearer " (i primi 7 caratteri)
                token = authHeader.substring(7);
            }
        }

       
        
        // Se il token è stato trovato e lo si vuole invalidare (logout)
        if (token != null && request.getRequestURI().equals("/api/auth/logout")) {
            TokenBlacklist.addToBlacklist(token); // Aggiungi il token alla blacklist
            System.out.println("Token invalidated and added to blacklist: " + token);
           
            // Pulisci il SecurityContext
            SecurityContextHolder.clearContext();
 
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

 
        
        
        
        // Se il token è nella blacklist, impedisci l'accesso
        if (token != null && TokenBlacklist.isBlacklisted(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);  // Token non valido
            return;
        }        
        
        
        // Se è stato trovato un token (sia dai cookie che dall'header), procede con la validazione
        if (token != null) {
            try {
                // Utilizza jwtUtil per estrarre il nome utente dal token
                String username = jwtUtil.extractUsername(token);
                
                // Se il nome utente è stato estratto e non esiste già un'autenticazione nel SecurityContext
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Carica i dettagli dell'utente usando il servizio dedicato (es. dal database)
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    // Valida il token confrontandolo con i dettagli dell'utente
                    if (jwtUtil.validateToken(token, userDetails)) {
                        // Crea un oggetto di autenticazione con i ruoli e le credenziali dell'utente
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                        
                        // Imposta l'autenticazione nel contesto di sicurezza di Spring
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("🔐 Authentication impostata nel SecurityContext: " + authToken);
                        System.out.println("JWT Auth nel SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());
                    }
                }
            } catch (UsernameNotFoundException e) {
                // Se l'utente non viene trovato, registra l'errore
                logger.error("Utente non trovato per il token JWT", e);
            } catch (Exception e) {
                // Gestisce eventuali altri errori nella gestione o validazione del token
                logger.error("Errore nella gestione del token JWT", e);
            }
        }
        
        // Prosegue con la catena dei filtri, indipendentemente dalla presenza o validità del token
        chain.doFilter(request, response);
        System.out.println("Filtro: richiesta inoltrata al controller");
    }
    private boolean validateToken(String token) {
        // Implementa la logica per validare il token (es. tramite JwtUtil)
        return true;
    }
}
