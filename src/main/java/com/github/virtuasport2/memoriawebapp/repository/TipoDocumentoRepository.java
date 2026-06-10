package com.github.virtuasport2.memoriawebapp.repository;

import com.github.virtuasport2.memoriawebapp.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, Long> {

    // Metodo per trovare un tipo documento per nome
    Optional<TipoDocumento> findByNome(String nome);

    // Metodo per trovare un tipo documento in base al creatore (Utente)
    Optional<TipoDocumento> findByCreatoDa_Id(Long creatoDaId);

    // Se necessario, puoi aggiungere query personalizzate con @Query:
    // @Query("SELECT t FROM TipoDocumento t WHERE t.nome = ?1")
    // Optional<TipoDocumento> findTipoDocumentoByNomeCustom(String nome);
}
