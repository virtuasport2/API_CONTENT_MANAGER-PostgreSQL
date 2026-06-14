package com.github.virtuasport2.memoriawebapp.dto;

/*A cosa serve davvero Request DTO
Serve quando il backend riceve dati dal client:
    POST → creare articolo
    PUT → aggiornare articolo
Esempio:
    POST /api/articoli
    → ArticoloRequest 

Differenza mentale semplice
❌ Senza DTO
JSON → Spring prova a indovinare Entity
✔ Con DTO
JSON → TU decidi come costruire Entity
9. Riassunto in una frase

👉 Il 500 nasce perché Spring NON sa trasformare autoreId in Utente autore automaticamente. */

public class ArticoloRequest {

    private String titolo;
    private String sottotitolo;
    private String testo;
    private String categoria;
    private Long documentoId; // può essere null

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getSottotitolo() {
        return sottotitolo;
    }

    public void setSottotitolo(String sottotitolo) {
        this.sottotitolo = sottotitolo;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

   

    public Long getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(Long documentoId) {
        this.documentoId = documentoId;
    }
}