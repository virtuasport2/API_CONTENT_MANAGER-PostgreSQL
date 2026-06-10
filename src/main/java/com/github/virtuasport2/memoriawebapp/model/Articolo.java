package com.github.virtuasport2.memoriawebapp.model;

import jakarta.persistence.*;
//import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "articolo")
//@NoArgsConstructor

public class Articolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titolo;

    @Column(length = 255)
    private String sottotitolo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String testo;

    @Column(length = 100)
    private String categoria;

    @Column(name = "data_creazione", updatable = false)
    private LocalDateTime dataCreazione = LocalDateTime.now();

    @Column(name = "stato_autorizzazione", length = 50)
    private String statoAutorizzazione = "bozza";

    @Column(name = "visibilita")
    private Boolean visibilita = false; // 0 = privato, 1 = pubblico

    // Relazione ManyToOne con Documento
    @ManyToOne
    @JoinColumn(name = "documento_id", referencedColumnName = "id")
    private Documento documento;

    // Relazione ManyToOne con Utente
    @ManyToOne
    @JoinColumn(name = "autore_id", foreignKey = @ForeignKey(name = "fk_articolo_utente"))
    private Utente autore;

    @Override
    public String toString() {
        return "Articolo [id=" + id + ", titolo=" + titolo + ", sottotitolo=" + sottotitolo + ", testo=" + testo
                + ", categoria=" + categoria + ", dataCreazione=" + dataCreazione + ", statoAutorizzazione="
                + statoAutorizzazione + ", visibilita=" + visibilita + ", autore=" + autore + ", documento=" + documento + "]";
    }
}
