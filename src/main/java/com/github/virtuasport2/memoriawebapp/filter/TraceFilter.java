package com.github.virtuasport2.memoriawebapp.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* ✔️ Risultato finale nei log

Se nel Service scrivi:

log.info("Creating user");

vedrai:

[123e4567] Creating user
 */



    /* @Override → Java puro (controllo del codice) */
    /*  @Component → Spring (registrazione nel sistema) */

@Component  /* registra la classe come Bean, Spring la inserisce nel container, il Filter entra nella pipeline */
public class TraceFilter extends OncePerRequestFilter {

    /*
     * private → usabile solo dentro la classe
     * static → appartiene alla classe, non all’oggetto
     * final → è una costante (non cambia)
     * "requestId" → chiave usata per MDC/log
     * 
     * 👉 Serve per avere una stringa fissa e sicura da usare nei log senza errori
     * di scrittura.
     */
    private static final String REQUEST_ID = "requestId";

    private static final Logger log = LoggerFactory.getLogger(TraceFilter.class);

    /*@Override dice a Java: questo metodo riscrive un metodo già esistente nella classe padre o interfaccia */
    @Override  /*  Annotazioni - @Override dice: “sto riscrivendo un metodo della classe padre” esempio: doFilterInternal 👉 serve solo a livello Java */
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        /* Genero ID unico */
        String requestId = UUID.randomUUID().toString();

        /* ID ora lo metto in MDC, da questo momento tutti i log lo “vedono” */
        MDC.put(REQUEST_ID, requestId);

        try {
            log.info("REQUEST START");
            /* la request continua verso Controller */
            filterChain.doFilter(request, response);
            log.info("REQUEST END");
            
        } finally {
            /* evita contaminazione tra richieste */
            MDC.remove(REQUEST_ID);
        }
    }
}

/*
 * ✔️ Struttura corretta
 * try {
 * // codice
 * } catch (Exception e) {
 * // gestione errore
 * } finally {
 * // sempre eseguito
 * }
 * ✔️ Significato
 * try
 * contiene il codice “a rischio errore”
 * catch
 * viene eseguito solo se c’è un errore
 * finally
 * viene eseguito sempre
 * anche se NON c’è errore
 * anche se c’è return o exception
 */


/*
✔️ Il motivo per cui usiamo finally NON dipende da Spring o dal Filter

Dipende da questa regola Java:

finally serve per eseguire codice sempre, indipendentemente da errori

✔️ Nel tuo caso (MDC)
try {
    filterChain.doFilter(request, response);
} finally {
    MDC.remove("requestId");
}
Perché finally è obbligatorio qui?

Perché vuoi garantire:

cleanup sempre
anche se il controller fallisce
anche se c’è eccezione
anche se la request viene interrotta
*/