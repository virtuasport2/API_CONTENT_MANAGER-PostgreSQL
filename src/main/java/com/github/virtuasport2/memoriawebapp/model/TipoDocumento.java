package com.github.virtuasport2.memoriawebapp.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
//import lombok.NoArgsConstructor;


@Entity
@Table(name = "tipo_documento")
//@NoArgsConstructor
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descrizione;

    @ManyToOne
    @JoinColumn(name = "creato_da", referencedColumnName = "id")
    private Utente creatoDa;

    @OneToMany(mappedBy = "tipoDocumento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Documento> documenti;

	public Long getId() {
		return id;
	}

//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Utente getCreatoDa() {
		return creatoDa;
	}

	public void setCreatoDa(Utente creatoDa) {
		this.creatoDa = creatoDa;
	}

	@Override
	public String toString() {
		return "TipoDocumento [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", creatoDa=" + creatoDa
				+ "]";
	}



}
