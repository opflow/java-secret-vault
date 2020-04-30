package com.devebot.jigsaw.vault;

import com.devebot.jigsaw.vault.core.VaultHandler;
import com.devebot.jigsaw.vault.utils.ClipboardUtil;
import com.devebot.jigsaw.vault.utils.StringUtil;
import java.io.Console;

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
        console.printf("[-] Your input secret: %s%n", StringUtil.maskPassword(secret));
        
        // encrypt the secret
        VaultHandler vaultHandler  = new VaultHandler();
        String vault = vaultHandler.encryptVault(secret);
        console.printf("[-] Vault Block:%n%s%n", vault);
        
        // copy the vault block to clipboard
        if (ClipboardUtil.copy(vault)) {
            console.printf("[-] Vault Block have been copied to clipboard. Paste it somewhere and press [Enter] to exit.");
            console.readLine();
        } else {
            console.printf("[-] Cannot copy the Vault Block to clipboard%n");
        }
    }
}
