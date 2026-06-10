package com.github.virtuasport2.memoriawebapp.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.virtuasport2.memoriawebapp.enums.Ruolo;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.repository.UtenteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository userRepository; // Supponendo che tu abbia un repository per gli utenti

    
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Utente user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Assegna il ruolo dell'utente
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getRuolo().name()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), 
                user.getPasswordHash(), 
                mapRolesToAuthorities(user.getRuolo())        
                //authorities
        );
    }    
        // Mappa i ruoli dell'enum in una lista di GrantedAuthority
        private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Ruolo ruolo) {
        	 return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + ruolo.name()));
             
        
    }
        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Utente user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPasswordHash(),
                    Collections.emptyList() // Qui puoi mettere i ruoli se necessario
            );
		}
}

