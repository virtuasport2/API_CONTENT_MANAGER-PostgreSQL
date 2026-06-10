package com.github.virtuasport2.memoriawebapp.repository;

import com.github.virtuasport2.memoriawebapp.model.Articolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticoloRepository extends JpaRepository<Articolo, Long> {

    // Trova articoli in base al titolo
    Optional<Articolo> findByTitolo(String titolo);

    // Trova articoli in base all'autore
    Optional<Articolo> findByAutore_Id(Long autoreId);

    // Trova articoli in base allo stato di autorizzazione
    Optional<Articolo> findByStatoAutorizzazione(String statoAutorizzazione);

    // Trova articoli in base alla visibilità
    Optional<Articolo> findByVisibilita(Boolean visibilita);

    // Se necessario, puoi aggiungere query personalizzate con @Query:
    // @Query("SELECT a FROM Articolo a WHERE a.categoria = ?1")
    // List<Articolo> findArticoliByCategoria(String categoria);
}
