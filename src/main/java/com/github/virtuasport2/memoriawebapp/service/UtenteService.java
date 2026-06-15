package com.github.virtuasport2.memoriawebapp.service;

import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.repository.UtenteRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {
	@Autowired
    private final UtenteRepository utenteRepository;
    
    // Inietto il PasswordEncoder che sarà usato per la gestione delle password
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Il costruttore non ha bisogno di essere modificato (l'encoder viene iniettato da Spring)
    public UtenteService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

//    Restituisce un'eccezione con HTTP 404 se l'utente non esiste.
//    Miglior gestione degli errori invece di restituire Optional<User>.    
    public Utente getUserByUsername(String username) {
        return utenteRepository.findByUsername(username)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        		}    

    public Utente getUserByEmail(String email) {
        return utenteRepository.findByEmail(email)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        		}     
    
    
    // Metodo per la verifica delle credenziali dell'utente
  public Utente verifyUser1(String username, String password) {

      return utenteRepository.findByUsername(username)
      		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }  
    
  
  // Metodo per la verifica delle credenziali dell'utente
public Utente verifyEmail(String email) {

	return utenteRepository.findByEmail(email)
		  .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));

  }  
    
    public boolean verifyUser(String email, String password) {
        
//    	String rawPassword = "tuapassword"; 
//    	String encodedPassword = passwordEncoder.encode(rawPassword);
//    	System.out.println("Encoded password: " + encodedPassword);
//    	System.out.println("Match: " + passwordEncoder.matches(rawPassword, encodedPassword));
    	
    	// Utente users = utenteRepository.findUtenteByUsername(username);
    	//Optional<Utente> userOpt = utenteRepository.findByUsername(username);
    	
    	//System.out.println("Entrato in verifyUser con username: " + username); // Debug        
        //Optional<Utente> userOpt = utenteRepository.findByUsername(username);
       // System.out.println(passwordEncoder.encode("tua_password"));
        

        //List<Utente> users = utenteRepository.findAllUsers();
        //users.forEach(u -> System.out.println("User: " + u.getUsername()));        
        
        //Optional<Utente> userOpt = utenteRepository.findByUsernameIgnoreCase(username);
        

        //System.out.println("Risultato query: " + userOpt);
        
    	//Utente utente = utenteRepository.findByEmail(email).get();
    // errore registrazione 
    Utente utente = utenteRepository.findByEmail(email)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email not found"));
     if (utente != null) {
 
        	System.out.println("Password inserita: " + password);
        	System.out.println("Hash generato al momento: " + passwordEncoder.encode(password));
        	System.out.println("Hash salvato nel database: " + utente.getPasswordHash());
        	System.out.println("Match: " + passwordEncoder.matches(password, utente.getPasswordHash()));
        	
        	
            // Usa il passwordEncoder per confrontare la password
            boolean matches = passwordEncoder.matches(password, utente.getPasswordHash());
            System.out.println("Password match: " + matches); // Debug
            return matches;  // Restituisce il risultato della verifica della password
        }else {
        	System.out.println("Utente non trovato");
        }

        return false; // Utente non trovato o password errata
    }  

    // Metodo di test per creare un utente e verificarne la password
    public void creareUtente() {
        String password = "mypassword";
        
        // Cripta la password usando l'encoder di Spring
        String passwordCriptata = passwordEncoder.encode(password);
        
        // Verifica che la password criptata corrisponda alla password di input
        boolean match = passwordEncoder.matches(password, passwordCriptata);
        
        System.out.println("La password è corretta? " + match);
    }   

    // Altri metodi per la gestione degli utenti

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public Optional<Utente> findById(Long id) {
        return utenteRepository.findById(id);
    }

    // Metodo per salvare un nuovo utente, assicurandosi di criptare la password prima
    public Utente saveUtente(Utente utente) {

        // Imposta la password hashata
        utente.setPasswordHash(utente.getPasswordHash());
        
        // Salva l'utente nel repository
        return utenteRepository.save(utente);
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }

    // recupera l'utente autenticato - mi serve per quando creo un nuovo Documento o Tipo Documento
    public Utente getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return utenteRepository.findByUsername(username)
                        .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            } else {
                return null; // Principal non valido
            }
        }
        return null; // Nessuna autenticazione presente
    }


}
