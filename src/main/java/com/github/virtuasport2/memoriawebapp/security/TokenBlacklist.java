package com.github.virtuasport2.memoriawebapp.security;

import java.util.HashSet;
import java.util.Set;

public class TokenBlacklist {
    private static final Set<String> blacklistedTokens = new HashSet<>();

    // Aggiungi un token alla blacklist
    public static void addToBlacklist(String token) {
        blacklistedTokens.add(token);
    }

    // Verifica se un token è nella blacklist
    public static boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }
}
