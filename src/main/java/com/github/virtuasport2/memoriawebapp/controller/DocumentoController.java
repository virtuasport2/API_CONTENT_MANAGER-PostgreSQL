package com.github.virtuasport2.memoriawebapp.controller;

import org.springframework.web.bind.annotation.*;

import com.github.virtuasport2.memoriawebapp.dto.DocumentoRequest;
import com.github.virtuasport2.memoriawebapp.model.Documento;
import com.github.virtuasport2.memoriawebapp.service.DocumentoService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/documenti")
public class DocumentoController {

    private final DocumentoService documentoService;

    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @GetMapping
    public ResponseEntity<List<Documento>> getAllDocumenti() {
        List<Documento> documenti = documentoService.findAll();

        if (documenti.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(documenti);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Long id) {
        Optional<Documento> documento = documentoService.getDocumentoById(id);

        return documento.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Documento> createDocumento(@RequestBody DocumentoRequest request) {

        System.out.println("Nome: " + request.getNome());
        System.out.println("Tipo: " + request.getTipo());
        System.out.println("Struttura JSON: " + request.getStrutturaJson());

        Documento savedDocumento = documentoService.saveDocumento(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> updateDocumento(
            @PathVariable Long id,
            @RequestBody DocumentoRequest request) {

        Documento updated = documentoService.updateDocumento(id, request);

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        if (documentoService.getDocumentoById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }


    
}