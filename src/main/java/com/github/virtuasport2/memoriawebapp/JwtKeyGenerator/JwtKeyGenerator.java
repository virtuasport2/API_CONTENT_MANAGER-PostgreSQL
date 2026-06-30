package com.github.virtuasport2.memoriawebapp.JwtKeyGenerator;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;

import javax.crypto.SecretKey;

/*
invece di eseguire la main di ingresso all'applicazione , eseguo questa

Problema

Stai usando JWT con HMAC-SHA256 nel backend.

Errore reale che hai visto:

WeakKeyException: key byte array is 24 bits
Significato tecnico
la tua jwt.secret è troppo corta
oppure non è una chiave valida Base64
quindi la libreria JWT rifiuta la firma
Conseguenza pratica
login funziona
ma quando passi da HTTP a WebSocket:
il token viene riletto
viene rieseguito extractUsername
fallisce la validazione chiave
handshake WebSocket può fallire (500)


secretkey genrata:2dbK4md7V8i9/fHAYk9ZYrSreCugjhYBv9YILb9+Dpw=
*/
public class JwtKeyGenerator {

    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String base64 = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64);
    }
}

/*
steps:
DEV → application-dev.properties
PROD → JWT_SECRET (env var)

1- per DEV

            Copyright (C) Microsoft Corporation. Tutti i diritti riservati.
            Prova la nuova PowerShell multipiattaforma https://aka.ms/pscore6

            PS C:\WINDOWS\system32> $env:JWT_SECRET="2dbK4md7V8i9/fHAYk9ZYrSreCugjhYBv9YILb9+Dpw="

                 Dopo averla settata,rimanere nella stessa sessione ed eseguire:
                    C:\WINDOWS\system32>echo $env:JWT_SECRET
                    Deve stampare la chiave.

nel file application-dev-properties , copio la chiave 
    # =========================
    # JWT (se lo usi in locale)
    # =========================
    jwt.secret=${JWT_SECRET}

*/


/*

2. PROD (corretto)

Dipende da dove deployi:

Linux server
export JWT_SECRET="..."
Docker
environment:
  - JWT_SECRET=...
Cloud (Azure/AWS)
→ secret manager
Concetto chiave
la chiave JWT NON si scrive a mano
NON deve essere corta
NON va rigenerata ogni avvio
deve essere stabile tra tutti i componenti (HTTP + WebSocket + Filter)
Riassunto logico
fase	cosa fai
sviluppo	generi chiave 1 volta
configurazione	la metti in properties
runtime	Spring la usa per firmare/verificare JWT
*/