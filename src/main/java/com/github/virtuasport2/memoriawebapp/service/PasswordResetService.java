package com.github.virtuasport2.memoriawebapp.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.virtuasport2.memoriawebapp.model.PasswordResetToken;
import com.github.virtuasport2.memoriawebapp.model.Utente;
import com.github.virtuasport2.memoriawebapp.repository.PasswordResetTokenRepository;
import com.github.virtuasport2.memoriawebapp.repository.UtenteRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class PasswordResetService {

    @Autowired
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UtenteRepository utenteRepository;
    private final EmailService emailService;

    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository, 
    							UtenteRepository utenteRepository,
                                EmailService emailService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.utenteRepository = utenteRepository;
        this.emailService = emailService;
    }

    @Transactional
    public void generatePasswordResetToken(String email, HttpServletRequest servletRequest) {
        Optional<Utente> userOpt = utenteRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return; // Non riveliamo se l'email esiste
        }

          Utente utente = userOpt.get();
          String token = UUID.randomUUID().toString();
 
          PasswordResetToken resetToken = new PasswordResetToken(token, utente, LocalDateTime.now().plusHours(1));
//        Quando un utente richiede un reset della password, 
//        il server invia un token e un link per completare l'operazione. 
//        Il server non ha bisogno di memorizzare la nuova password in questa fase, 
//        ma solo il token, che è temporaneo e non utilizzabile per altri scopi.       
          passwordResetTokenRepository.save(resetToken);

          String resetUrl = servletRequest.getRequestURL().toString().replace("forgot-password", "reset-password") + "?token=" + token;
          emailService.sendResetPasswordEmail(utente.getEmail(), resetUrl);
    }
}
