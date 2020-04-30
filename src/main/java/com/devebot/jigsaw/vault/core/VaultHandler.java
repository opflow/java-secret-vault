package com.devebot.jigsaw.vault.core;

import com.devebot.jigsaw.vault.crypto.CipherFactory;
import com.devebot.jigsaw.vault.crypto.CipherInterface;
import com.devebot.jigsaw.vault.utils.PasswordLoader;
import com.devebot.jigsaw.vault.utils.StringUtil;

public class VaultHandler {
    
    private static final String DEFAULT_HEADER = VaultHeader.VAULT_FORMAT_ID + VaultHeader.HEADER_SEPARATOR
        + VaultHeader.VAULT_FORMAT_VERSION_1_1 + VaultHeader.HEADER_SEPARATOR
        + CipherInterface.ALGO_AES256;
    
    private final PasswordLoader passwordLoader = new PasswordLoader();
    
    public boolean isVaultBlock(String vaultBlock) {
        return VaultHeader.isVaultBlock(vaultBlock);
    }
    
    public String decryptVault(String vaultBlock) {
        return decryptVault(vaultBlock, passwordLoader.getVaultPassword());
    }
    
    public String decryptVault(String vaultBlock, String password) {
        if (!VaultHeader.isVaultBlock(vaultBlock)) {
            return vaultBlock;
        }
        
        VaultHeader header = VaultParser.parseHeader(vaultBlock);
        
        if (!header.isValid()) {
            throw new RuntimeException("Packet is not an Encrypted Ansible Vault");
        }

        VaultPayload payload = VaultParser.parsePayload(vaultBlock);

        return StringUtil.newString(CipherFactory.getCipher(header.getAlgorithm()).decrypt(payload, password));
    }
    
    public String encryptVault(String plainText) {
        return encryptVault(plainText, passwordLoader.getVaultPassword());
    }
    
    public String encryptVault(String plainText, String password) {
        if (plainText == null) return plainText;
        VaultPayload payload = CipherFactory.getCipher(CipherInterface.ALGO_AES256).encrypt(plainText.getBytes(), password);
        return DEFAULT_HEADER + StringUtil.LINE_BREAK + payload.toString();
    }
}
