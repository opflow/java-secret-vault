package com.devebot.jigsaw.vault.utils;

import com.devebot.jigsaw.vault.exceptions.NullPasswordException;

public class PasswordLoader {
    public static final String VAULT_PASSWORD_ENV_VAR = "JAVA_ANSIBLE_VAULT_PASSWORD";
    public static final String VAULT_PASSWORD_PROP_NAME = "java.ansible.vault.password";
    
    private String vaultPassword = null;
    
    public synchronized String getVaultPassword() {
        if (vaultPassword == null) {
            String _password = SystemUtil.getEnvironVariable(VAULT_PASSWORD_ENV_VAR);
            
            if (_password == null) {
                _password = SystemUtil.getSystemProperty(VAULT_PASSWORD_PROP_NAME);
            }
            
            if (_password == null) {
                String err = StringUtil.format("Cannot find password in environment variable [{0}] and property [{1}]",
                    VAULT_PASSWORD_ENV_VAR,
                    VAULT_PASSWORD_PROP_NAME
                );
                throw new NullPasswordException(err);
            }
            
            vaultPassword = _password;
        }
        return vaultPassword;
    }
}
