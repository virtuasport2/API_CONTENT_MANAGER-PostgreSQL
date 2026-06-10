//package com.github.virtuasport2.memoriawebapp.controller;
//
//import java.security.NoSuchAlgorithmException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.web.bind.annotation.*;
//
//import com.github.virtuasport2.memoriawebapp.security.CustomUserDetailsService;
//import com.github.virtuasport2.memoriawebapp.security.JwtUtil;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
// 
//    
//    @PostMapping("/login")
//    public String login(@RequestParam String username, @RequestParam String password) throws NoSuchAlgorithmException {
//    	System.out.println("Login attempt for user: " + username); // 👀 Debug
//    	authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        return JwtUtil.generateToken(userDetails);
//    }
//}
