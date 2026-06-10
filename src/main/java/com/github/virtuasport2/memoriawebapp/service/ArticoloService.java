package com.github.virtuasport2.memoriawebapp.service;

import com.github.virtuasport2.memoriawebapp.model.Articolo;
import com.github.virtuasport2.memoriawebapp.repository.ArticoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticoloService {

    @Autowired
    private ArticoloRepository articoloRepository;

    public List<Articolo> findAll() {
        return articoloRepository.findAll();
    }

    public Optional<Articolo> findById(Long id) {
        return articoloRepository.findById(id);
    }

    public Articolo save(Articolo articolo) {
        return articoloRepository.save(articolo);
    }

    public void deleteById(Long id) {
        articoloRepository.deleteById(id);
    }
}
