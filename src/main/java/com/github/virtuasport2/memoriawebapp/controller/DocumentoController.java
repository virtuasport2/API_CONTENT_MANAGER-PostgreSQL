package com.github.virtuasport2.memoriawebapp.controller;

import org.springframework.web.bind.annotation.*;

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
        //List<Documento> documenti = documentoService.getAllDocumenti();
    	List<Documento> documenti = documentoService.findAll();
        if (documenti.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content se la lista è vuota
        }
        // solo per i test
        // 1️⃣ Stampa l'oggetto prima del return
    //    System.out.println("Documento trovato: " + documenti);
        
    
        // fine test
        return ResponseEntity.ok(documenti);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Long id) {
        Optional<Documento> documento = documentoService.getDocumentoById(id);
        		
        return documento.map(ResponseEntity::ok)
                        .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Documento> createDocumento(@RequestBody Documento documento) {
    	// solo in fase di test
        // Verifica i valori ricevuti
        System.out.println("Nome: " + documento.getNome());
        System.out.println("Tipo: " + documento.getTipo());
        System.out.println("Struttura JSON: " + documento.getStrutturaJson());    	
    	
        Documento savedDocumento = documentoService.saveDocumento(documento);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDocumento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Documento> updateDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        if (!documentoService.getDocumentoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        documento.setId(id);
        Documento updatedDocumento = documentoService.saveDocumento(documento);
        return ResponseEntity.ok(updatedDocumento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        if (!documentoService.getDocumentoById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
