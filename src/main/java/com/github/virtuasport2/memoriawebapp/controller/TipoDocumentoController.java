package com.github.virtuasport2.memoriawebapp.controller;

import com.github.virtuasport2.memoriawebapp.model.TipoDocumento;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.service.TipoDocumentoService;
import com.github.virtuasport2.memoriawebapp.service.UtenteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tipi-documento")
public class TipoDocumentoController {

    @Autowired
    private TipoDocumentoService tipoDocumentoService;

    @Autowired
    private UtenteService utenteService;    
    
    @GetMapping
    public List<TipoDocumento> getAllTipoDocumenti() {
        return tipoDocumentoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> getTipoDocumentoById(@PathVariable Long id) {
        Optional<TipoDocumento> tipoDocumento = tipoDocumentoService.findById(id);
        return tipoDocumento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoDocumento> createTipoDocumento(@RequestBody TipoDocumento tipoDocumento) {
        // Ottieni l'utente autenticato tramite il servizio UtenteService
        Utente utente = utenteService.getAuthenticatedUser();
        if (utente != null) {
            return new ResponseEntity<>(tipoDocumentoService.save(tipoDocumento, utente), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    	
    	
    	
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipoDocumento(@PathVariable Long id) {
        tipoDocumentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
