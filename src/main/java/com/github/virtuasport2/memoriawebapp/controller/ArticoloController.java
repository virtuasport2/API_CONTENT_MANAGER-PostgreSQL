package com.github.virtuasport2.memoriawebapp.controller;

import com.github.virtuasport2.memoriawebapp.model.Articolo;
import com.github.virtuasport2.memoriawebapp.service.ArticoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/articoli")
public class ArticoloController {

    @Autowired
    private ArticoloService articoloService;

    @GetMapping
    public List<Articolo> getAllArticoli() {
        return articoloService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articolo> getArticoloById(@PathVariable Long id) {
        Optional<Articolo> articolo = articoloService.findById(id);
        return articolo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Articolo> createArticolo(@RequestBody Articolo articolo) {
        return new ResponseEntity<>(articoloService.save(articolo), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticolo(@PathVariable Long id) {
        articoloService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
