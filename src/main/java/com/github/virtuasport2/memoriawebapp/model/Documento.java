package com.github.virtuasport2.memoriawebapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

//import lombok.Getter;
//import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;

import com.github.virtuasport2.memoriawebapp.enums.Stato;

@Entity
@Table(name = "documento")
// @Getter
// @Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // Per includere solo i campi non nulli
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 255)
	private String nome;

	@Column(nullable = false, length = 100)
	private String tipo;

	@Column(columnDefinition = "TEXT")
	private String descrizione;

	@Column(columnDefinition = "json")
	@org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
	private String strutturaJson;

	@ManyToOne
	@JoinColumn(name = "creato_da", referencedColumnName = "id")
	@OnDelete(action = OnDeleteAction.SET_NULL) // Imposta l'azione di delete su SET_NULL
	@JsonIgnore
	private Utente creatoDa;

	@ManyToOne
	@JoinColumn(name = "tipo_id", referencedColumnName = "id", nullable = false)
	@NotNull(message = "Il tipo documento è obbligatorio")
	private TipoDocumento tipoDocumento;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Stato stato;

	@OneToMany(mappedBy = "documento")
	@JsonIgnore
	private List<Articolo> articoli;

	// Lombok non sempre generete tutti i getter e setter ,da vedere perchè, li
	// aggiungo manualmente

	public void setId(Long id) {
		this.id = id;
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

	public Utente getCreatoDa() {
		return creatoDa;
	}

	public void setCreatoDa(Utente creatoDa) {
		this.creatoDa = creatoDa;
	}

	public String getStrutturaJson() {
		return strutturaJson;
	}

	public void setStrutturaJson(String strutturaJson) {
		this.strutturaJson = strutturaJson;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public Stato getStato() {
		return stato;
	}

	public void setStato(Stato stato) {
		this.stato = stato;
	}

	public List<Articolo> getArticoli() {
		return articoli;
	}

	public void setArticoli(List<Articolo> articoli) {
		this.articoli = articoli;
	}

	@Override
	public String toString() {
		return "Documento [id=" + id + ", nome=" + nome + ", tipo=" + tipo + ", descrizione=" + descrizione
				+ ", strutturaJson=" + strutturaJson + ", creatoDa=" + creatoDa + ", tipoDocumento=" + tipoDocumento
				+ ", stato=" + stato + ", articoli=" + articoli + "]";
	}

}
