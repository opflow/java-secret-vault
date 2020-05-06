package com.devebot.jigsaw.vault.utils;

import com.devebot.jigsaw.vault.exceptions.NullPasswordException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordLoader {
    private final static Logger LOG = LoggerFactory.getLogger(PasswordLoader.class);
    
    public static final String VAULT_PASSWORD_LEVEL_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD_LEVEL";
    public static final String VAULT_PASSWORD_LEVEL_SYS_PROP = "java.secret.vault.password.level";
    
    public static final String VAULT_PASSWORD_FILE_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD_FILE";
    public static final String VAULT_PASSWORD_FILE_SYS_PROP = "java.secret.vault.password.file";
    
    public static final String VAULT_PASSWORD_SCRIPT_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD_SCRIPT";
    public static final String VAULT_PASSWORD_SCRIPT_SYS_PROP = "java.secret.vault.password.script";
    
    public static final String VAULT_PASSWORD_TEXT_ENV_NAME = "JAVA_SECRET_VAULT_PASSWORD";
    public static final String VAULT_PASSWORD_TEXT_SYS_PROP = "java.secret.vault.password";
    
    public static final String STRONG_PASSWD_PATTERN_STRING = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*])(?=\\S{8,})";
    public static final Pattern STRONG_PASSWD_PATTERN = Pattern.compile(STRONG_PASSWD_PATTERN_STRING);
    
    public static final String MEDIUM_PASSWD_PATTERN_STRING = "^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=\\S{6,})";
    public static final Pattern MEDIUM_PASSWD_PATTERN = Pattern.compile(MEDIUM_PASSWD_PATTERN_STRING);
    
    public static final String SIMPLE_PASSWD_PATTERN_STRING = "^(?=\\S{6,})";
    public static final Pattern SIMPLE_PASSWD_PATTERN = Pattern.compile(SIMPLE_PASSWD_PATTERN_STRING);
    
    private String selectedPasswordLevel = null;
    private String selectedPasswordSource = null;
    private String vaultPassword = null;

    public PasswordLoader() {
        selectedPasswordLevel = SystemUtil.getEnv(VAULT_PASSWORD_LEVEL_SYS_PROP, VAULT_PASSWORD_LEVEL_ENV_NAME, "simple");
    }
    
    public synchronized String getVaultPassword() {
        if (vaultPassword == null) {
            String _password = loadPasswordFromFile();
            if (isOk(_password)) {
                return (vaultPassword = _password);
            }

            _password = loadPasswordFromScript();
            if (isOk(_password)) {
                return (vaultPassword = _password);
            }

            _password = loadPasswordFromEnv();
            if (isOk(_password)) {
                return (vaultPassword = _password);
            }

            throw new NullPasswordException("VaultPassword not found");
        }
        return vaultPassword;
    }
    
    public String getPasswordSource() {
        return selectedPasswordSource;
    }
    
    public String getValidationLevel() {
        return selectedPasswordLevel;
    }
    
    public boolean validatePassword(String password) {
        return validatePassword(password, selectedPasswordLevel);
    }
    
    public boolean validatePassword(String password, String level) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("VaultPassword: {}", StringUtil.maskPassword(password));
        }
        if (password == null) {
            return false;
        }
        Pattern passwordPattern;
        switch (level) {
            case "strong":
                passwordPattern = STRONG_PASSWD_PATTERN;
                break;
            case "medium":
                passwordPattern = MEDIUM_PASSWD_PATTERN;
                break;
            default:
                passwordPattern = SIMPLE_PASSWD_PATTERN;
                break;
        }
        return passwordPattern.matcher(password).find();
    }
    
    protected String loadPasswordFromFile() {
        String filePath = SystemUtil.getEnv(VAULT_PASSWORD_FILE_SYS_PROP, VAULT_PASSWORD_FILE_ENV_NAME);
        
        if (filePath != null) {
            try {
                selectedPasswordSource = "file[" + filePath + "]";
                return StringUtil.newString(FileUtil.readAll(filePath));
            } catch (Exception e) {
                SystemUtil.printStackTrace(e);
                return null;
            }
        }
        
        return null;
    }
    
    protected String loadPasswordFromScript() {
        String scriptPath = SystemUtil.getEnv(VAULT_PASSWORD_SCRIPT_SYS_PROP, VAULT_PASSWORD_SCRIPT_ENV_NAME);
        
        if (scriptPath != null) {
            try {
                selectedPasswordSource = "script[" + scriptPath + "]";
                return ShellRunner.executeCommand(scriptPath);
            } catch (Exception e) {
                SystemUtil.printStackTrace(e);
                return null;
            }
        }
        
        return null;
    }
    
    protected String loadPasswordFromEnv() {
        selectedPasswordSource = "text[" + VAULT_PASSWORD_TEXT_SYS_PROP + "/" + VAULT_PASSWORD_TEXT_ENV_NAME + "]";
        return SystemUtil.getEnv(VAULT_PASSWORD_TEXT_SYS_PROP, VAULT_PASSWORD_TEXT_ENV_NAME);
    }
    
    private boolean isOk(String password) {
        boolean ok = password != null && !password.isEmpty();
        if (ok) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("Vault-Password has been loaded from {}", selectedPasswordSource);
            }
        }
        return ok;
    }
}
