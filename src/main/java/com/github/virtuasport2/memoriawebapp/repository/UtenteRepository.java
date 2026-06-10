package com.github.virtuasport2.memoriawebapp.repository;



import com.github.virtuasport2.memoriawebapp.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

	//Utente findUtenteByUsername(String username); //con api rest usare optional
    // Metodo per trovare un utente per username
    Optional<Utente> findByUsername(String username);

	
    @Query("SELECT u FROM Utente u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<Utente> findByUsernameIgnoreCase(@Param("username") String username);

//    @Query("SELECT u FROM Utente u")
//    List<Utente> findAllUsers();
    
    
    // Metodo per trovare un utente per email
    Optional<Utente> findByEmail(String email);

    // Metodo per trovare un utente per ruolo (ad esempio AUTORE, REVISORE, ADMIN)
    Optional<Utente> findByRuolo(String ruolo);

    // Se necessario, puoi aggiungere query personalizzate con @Query:
    // @Query("SELECT u FROM Utente u WHERE u.email = ?1")
    // Optional<Utente> findUtenteByEmailCustom(String email);

}
