package com.github.virtuasport2.memoriawebapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.Authentication;
/*
Significato reale
@RestController = @Controller + @ResponseBody
traduzione pratica:
@Controller → gestisce richieste HTTP
@ResponseBody → ritorna JSON (non pagina HTML)
*/
@RestController
@RequestMapping("/api")
public class UserContextController {

    @GetMapping("/me")
    public String me(Authentication authentication) {
        return authentication.getName();
    }
}