package com.devebot.jigsaw.vault;

import com.devebot.jigsaw.vault.core.VaultHandler;
import java.io.Console;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // get the secret (password) from the console
        Console console = System.console();
        if (console == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        char[] passwordArray = console.readPassword("[+] Enter your secret: ");
        String secret = new String(passwordArray);
        console.printf("[-] Your input secret: %s%n", maskPassword(secret));
        
        // encrypt the secret
        VaultHandler vaultHandler  = new VaultHandler();
        String vault = vaultHandler.encryptVault(secret);
        console.printf("[-] Vault Block:%n%s%n", vault);
    }
    
    public static String maskPassword(String password) {
        if (password == null) return null;
        char[] charArray = new char[password.length()];
        Arrays.fill(charArray, '*');
        return new String(charArray);
    }
}
