package com.github.virtuasport2.memoriawebapp.controller;

import com.github.virtuasport2.memoriawebapp.dto.UtenteRequest;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.service.UtenteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utenti")
public class UtenteController {


    private final UtenteService utenteService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public UtenteController(UtenteService utenteService) {
	
		this.utenteService = utenteService;
	}

	@GetMapping
    public List<Utente> getAllUtenti() {
        return utenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utente> getUtenteById(@PathVariable Long id) {
        Optional<Utente> utente = utenteService.findById(id);
        return utente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping 
    public ResponseEntity<Utente> createUtente(@RequestBody UtenteRequest request) {

        // Log per controllare il valore dell'email
        System.out.println("Email ricevuta: " + request.getEmail());
        System.out.println("Username ricevuto: " + request.getUsername());
        
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body(null);  // Restituisce un errore 400 se l'email è mancante
        }    	
        // Crea un oggetto Utente da UtenteRequest
        Utente utente = new Utente();
        utente.setUsername(request.getUsername());
        utente.setEmail(request.getEmail()); // Verifica che email non sia null
        utente.setRuolo(request.getRuolo());
        String password = request.getPassword();
        
        // Cripta la password prima di salvarla
        String passwordCriptata = passwordEncoder.encode(password);
        utente.setPasswordHash(passwordCriptata);
    
        // Salva l'utente nel database tramite il servizio
        Utente saveUtente = utenteService.saveUtente(utente);  // Passa l'oggetto Utente
        return ResponseEntity.status(HttpStatus.CREATED).body(saveUtente);   
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
        utenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
