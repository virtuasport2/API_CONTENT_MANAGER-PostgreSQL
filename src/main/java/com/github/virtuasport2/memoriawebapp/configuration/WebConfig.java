package com.github.virtuasport2.memoriawebapp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/api/**") // Definisci il percorso delle tue API
                .allowedOrigins("http://localhost:3000",
                		        "http://localhost:8081",
                		        "http://127.0.0.1:5500",
                		        "moz-extension://*") // Il dominio del tuo frontend
           //     .allowedOrigins("*")  // Permetti tutte le origini
                
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type")
                .allowedHeaders("*")
                .allowCredentials(true); // Se hai bisogno di inviare cookie o sessioni
       
       
       
       
    }
}
