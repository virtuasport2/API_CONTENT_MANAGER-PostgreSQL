package com.github.virtuasport2.memoriawebapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import com.github.virtuasport2.memoriawebapp.enums.Ruolo;

import jakarta.validation.constraints.Email;

public class UtenteRequest {


    @NotBlank(message = "Il campo username è obbligatorio")
    @Size(min = 4, max = 50, message = "Il campo username deve avere tra 4 e 50 caratteri")
    private String username;

    @NotBlank(message = "Il campo password è obbligatorio")
    @Size(min = 6, max = 100, message = "La password deve essere lunga tra 6 e 100 caratteri")
    private String password;

    @NotBlank(message = "Il campo email è obbligatorio")
    @Email(message = "Il campo email deve essere un'email valida")
    private String email;
   

    private Ruolo ruolo;
    
    // Getters e Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

	@Override
	public String toString() {
		return "UtenteRequest [username=" + username + ", password=" + password + ", email=" + email + ", ruolo="
				+ ruolo + "]";
	}


    
}