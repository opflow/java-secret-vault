package com.devebot.jigsaw.vault.core;

import com.devebot.jigsaw.vault.crypto.CipherFactory;
import com.devebot.jigsaw.vault.crypto.CipherInterface;
import com.devebot.jigsaw.vault.exceptions.NullPasswordException;
import com.devebot.jigsaw.vault.utils.StringUtil;
import com.devebot.jigsaw.vault.utils.SystemUtil;
import java.text.MessageFormat;

public class VaultHandler {
    
    public static final String VAULT_PASSWORD_ENV_VAR = "JAVA_ANSIBLE_VAULT_PASSWORD";
    public static final String VAULT_PASSWORD_PROP_NAME = "java.ansible.vault.password";
    private static final String DEFAULT_HEADER = VaultHeader.VAULT_FORMAT_ID + ";"
        + VaultHeader.VAULT_FORMAT_VERSION_1_1 + ";"
        + CipherInterface.ALGO_AES256;
    
    public boolean isVaultBlock(String vaultBlock) {
        return VaultHeader.isVaultBlock(vaultBlock);
    }
    
    public String decryptVault(String vaultBlock) {
        return decryptVault(vaultBlock, getVaultPassword());
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
        return encryptVault(plainText, getVaultPassword());
    }
    
    public String encryptVault(String plainText, String password) {
        VaultPayload payload = CipherFactory.getCipher(CipherInterface.ALGO_AES256).encrypt(plainText.getBytes(), password);
        return DEFAULT_HEADER + "\n" + payload.toString();
    }
    
    private String vaultPassword = null;
    
    private synchronized String getVaultPassword() {
        if (vaultPassword == null) {
            String _password = SystemUtil.getEnvironVariable(VAULT_PASSWORD_ENV_VAR);
            
            if (_password == null) {
                _password = SystemUtil.getSystemProperty(VAULT_PASSWORD_PROP_NAME);
            }
            
            if (_password == null) {
                String err = MessageFormat.format("Cannot find password in environment variable [{}] and property [{}]", new Object[] {
                    VAULT_PASSWORD_ENV_VAR, VAULT_PASSWORD_PROP_NAME
                });
                throw new NullPasswordException(err);
            }
            
            vaultPassword = _password;
        }
        return vaultPassword;
    }
}
