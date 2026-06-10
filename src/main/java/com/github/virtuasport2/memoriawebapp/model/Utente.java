package com.github.virtuasport2.memoriawebapp.model;

import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Objects;

import com.github.virtuasport2.memoriawebapp.enums.Ruolo;

@Entity
@Table(name = "utente")
//@Getter
//@Setter
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Ruolo ruolo = Ruolo.autore;

    @Column(name = "data_creazione", nullable = false, updatable = false)
    private LocalDateTime dataCreazione = LocalDateTime.now();

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean enabled = true; // 1 = Attivo, 0 = Disabilitato
    
	public Long getId() {
		return id;
	}

//	public void setId(Integer id) {
//		this.id = id;
//	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Ruolo getRuolo() {
		return ruolo;
	}

	public void setRuolo(Ruolo ruolo) {
		this.ruolo = ruolo;
	}

	public LocalDateTime getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDateTime dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Utente [id=" + id + ", username=" + username + ", passwordHash=" + passwordHash + ", email=" + email
				+ ", ruolo=" + ruolo + ", dataCreazione=" + dataCreazione + ", enabled=" + enabled + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataCreazione, email, enabled, id, passwordHash, ruolo, username);
	}








}
