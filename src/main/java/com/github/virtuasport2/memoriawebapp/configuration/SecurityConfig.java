package com.github.virtuasport2.memoriawebapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.authentication.AuthenticationManager;
import com.github.virtuasport2.memoriawebapp.security.JwtAuthenticationFilter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.config.Customizer;

import java.util.List;

//Indica a Spring che questa classe contiene delle configurazioni
@Configuration
// Abilita il supporto di Spring Security per l'applicazione web
@EnableWebSecurity
public class SecurityConfig {

    // Inietta il filtro personalizzato che gestisce l'autenticazione basata su JWT
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    // Definisce un bean che rappresenta la catena di filtri di sicurezza da
    // applicare alle richieste HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                // Disabilita la protezione CSRF: non necessaria per API REST che utilizzano
                // token (JWT)
                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                .logout(logout -> logout.disable()) // Disabilita il LogoutFilter di Spring

                // Configura le regole di autorizzazione per le richieste HTTP
                .authorizeHttpRequests(auth -> auth
                        // Permette l'accesso anonimo agli endpoint di tipo ERROR e FORWARD
                        .dispatcherTypeMatchers(DispatcherType.ERROR, DispatcherType.FORWARD).permitAll()

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/logout",
                                "/api/utenti",
                                "/api/tipi-documento",
                                "/api/articoli",
                                "/api/documento")
                        .permitAll()

                        // Richiede che tutte le altre richieste siano eseguite da utenti autenticati
                        .anyRequest().authenticated()

                )
                // Inserisce il filtro JWT personalizzato prima del filtro standard che gestisce
                // l'autenticazione basata su username/password
                // In questo modo, il filtro JWT ha la possibilità di processare il token e
                // impostare l'autenticazione prima che il filtro standard intervenga
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // .logout(logout -> logout
        // .logoutUrl("/api/auth/logout")
        // .logoutSuccessHandler((request, response, authentication) -> {
        // response.setStatus(HttpServletResponse.SC_OK);
        // })
        // .deleteCookies("JSESSIONID") // Se ci sono cookie da eliminare
        // .invalidateHttpSession(false) // Poiché stai usando JWT, non invalidare la
        // sessione
        //
        // );

        // Costruisce e restituisce la SecurityFilterChain configurata
        return http.build();
    }

    // Questo bean serve a definire esplicitamente le regole CORS lato backend
    // Spring.
    /*
     * 1- Dice da quale frontend è permesso chiamare il backend
     * 2- Permette tutti i metodi HTTP
     * 3- Permette tutti gli header
     * 4- Permette cookie / auth condivisa
     */
    @Bean
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();

        config.setAllowedOriginPatterns(List.of(
                "http://localhost:*",
                "http://127.0.0.1:*",
                "https://frontendcontentmanager-postgresql.onrender.com",
                "https://frontendcontentmanager-postgresql-test.onrender.com"));

        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // Definisce un bean di tipo PasswordEncoder che utilizza BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
    // Exception {
    // http.csrf(csrf -> csrf.disable())
    // .authorizeHttpRequests(requests -> requests
    // .anyRequest().permitAll() // Permette tutte le richieste senza autenticazione
    // // .requestMatchers("/api/auth/**").permitAll() // Permetti login e
    // registrazione senza token
    // // .requestMatchers("/api/utenti/**").permitAll()
    // // .and()
    //// .formLogin()
    //// .loginPage("/login") // la pagina di login
    //// .usernameParameter("username")
    //// .passwordParameter("password")
    //// .permitAll()
    //
    //// .anyRequest().authenticated())
    //// .sessionManagement(management ->
    // management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //// .addFilterBefore(jwtFilter, AnonymousAuthenticationFilter.class);
    // )
    // .sessionManagement(session ->
    // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    //
    // return http.build();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}

// ******************************************************************************************************

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {
//
// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .authorizeHttpRequests(auth -> auth
// .anyRequest().permitAll() // Permette tutte le richieste
// )
// .csrf(csrf -> csrf.disable()) // Disabilita CSRF (non necessario per API
// REST)
// .sessionManagement(session ->
// session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//
// return http.build();
// }
// }

// ******************************************************************************************************

// @Bean
// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
// Exception {
// http
// .authorizeHttpRequests(authorize -> authorize
// .requestMatchers("/api/documenti","/login", "/resources/**", "/css/**",
// "/js/**", "/images/**", "/public-api/**")
// .permitAll() // Permetti accesso alle risorse statiche
//
// .requestMatchers("/api/documenti/**")
// .authenticated() // Richiede autenticazione per tutte le altre pagine
// //.permitAll() // Nessuna autenticazione richiesta per /api/**
// //.anyRequest() // Qualsiasi altra richiesta
// //.permitAll()
// // .authenticated() // Autenticazione necessaria per il resto
//
//
// )
//
// .httpBasic(basic -> basic.disable()) // Disabilita l'autenticazione di base
// .formLogin(login -> login.disable()); // Disabilita il login tramite form
//
//
//
//
//
// return http.build();
// }
//

//
