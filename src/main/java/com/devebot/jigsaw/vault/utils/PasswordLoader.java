package com.devebot.jigsaw.vault.utils;

import com.devebot.jigsaw.vault.exceptions.NullPasswordException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordLoader {
    private final static Logger LOG = LoggerFactory.getLogger(PasswordLoader.class);
    
    public static final String VAULT_PASSWORD_FILE_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD_FILE";
    public static final String VAULT_PASSWORD_FILE_SYS_PROP = "java.secret.vault.password.file";
    
    public static final String VAULT_PASSWORD_SCRIPT_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD_SCRIPT";
    public static final String VAULT_PASSWORD_SCRIPT_SYS_PROP = "java.secret.vault.password.script";
    
    public static final String VAULT_PASSWORD_TEXT_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD";
    public static final String VAULT_PASSWORD_TEXT_SYS_PROP = "java.secret.vault.password";
    
    private String vaultPassword = null;
    
    public synchronized String getVaultPassword() {
        if (vaultPassword != null) {
            return vaultPassword;
        }
        
        vaultPassword = loadPasswordFromFile();
        if (vaultPassword != null) {
            validatePassword(vaultPassword);
            return vaultPassword;
        }
        
        vaultPassword = loadPasswordFromScript();
        if (vaultPassword != null) {
            validatePassword(vaultPassword);
            return vaultPassword;
        }
        
        vaultPassword = loadPasswordFromEnv();
        if (vaultPassword != null) {
            validatePassword(vaultPassword);
            return vaultPassword;
        }
        
        throw new NullPasswordException("VaultPassword not found");
    }
    
    private boolean validatePassword(String password) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("[-] VaultPassword: {}", StringUtil.maskPassword(password));
        }
        return true;
    }
    
    private String loadPasswordFromFile() {
        String filePath = SystemUtil.getEnv(VAULT_PASSWORD_FILE_SYS_PROP, VAULT_PASSWORD_FILE_ENV_NAME);
        
        if (filePath != null) {
            try {
                return String.join(StringUtil.LINE_BREAK, Files.readAllLines(Path.of(filePath)));
            } catch (Exception e) {
                SystemUtil.printStackTrace(e);
                return null;
            }
        }
        
        return null;
    }
    
    private String loadPasswordFromScript() {
        String scriptPath = SystemUtil.getEnv(VAULT_PASSWORD_SCRIPT_SYS_PROP, VAULT_PASSWORD_SCRIPT_ENV_NAME);
        
        if (scriptPath != null) {
            try {
                return ShellRunner.executeCommand(scriptPath);
            } catch (Exception e) {
                SystemUtil.printStackTrace(e);
                return null;
            }
        }
        
        return null;
    }
    
    private String loadPasswordFromEnv() {
        return SystemUtil.getEnv(VAULT_PASSWORD_TEXT_SYS_PROP, VAULT_PASSWORD_TEXT_ENV_NAME);
    }
}
