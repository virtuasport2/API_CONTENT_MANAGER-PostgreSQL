package com.github.virtuasport2.memoriawebapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.virtuasport2.memoriawebapp.model.Documento;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> {

	Optional<Documento> findById(Long id);
    // Aggiungi metodi personalizzati se necessario, ad esempio:
    // Optional<Documento> findByNome(String nome);
}

