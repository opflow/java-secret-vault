package com.devebot.jigsaw.vault.exceptions;

public class SecretVaultException extends RuntimeException {
    public SecretVaultException() {
        super();
    }

    public SecretVaultException(String message) {
        super(message);
    }

    public SecretVaultException(String message, Throwable cause) {
    	super(message, cause);
    }

    public SecretVaultException(Throwable cause) {
        super(cause);
    }

    protected SecretVaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
