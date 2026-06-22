package com.github.virtuasport2.memoriawebapp.dto;

public class DocumentoRequest {


private String nome;
private String tipo;
private String descrizione;
private String strutturaJson;


Long tipoDocumentoId;

private String stato;

public DocumentoRequest() {
}

public String getNome() {
    return nome;
}

public void setNome(String nome) {
    this.nome = nome;
}

public String getTipo() {
    return tipo;
}

public void setTipo(String tipo) {
    this.tipo = tipo;
}

public String getDescrizione() {
    return descrizione;
}

public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
}

public String getStrutturaJson() {
    return strutturaJson;
}

public void setStrutturaJson(String strutturaJson) {
    this.strutturaJson = strutturaJson;
}



public Long getTipoDocumentoId() {
    return tipoDocumentoId;
}

public void setTipoDocumentoId(Long tipoDocumentoId) {
    this.tipoDocumentoId = tipoDocumentoId;
}

public String getStato() {
    return stato;
}

public void setStato(String stato) {
    this.stato = stato;
}


}
