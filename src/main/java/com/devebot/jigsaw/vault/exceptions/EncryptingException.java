package com.devebot.jigsaw.vault.exceptions;

public class EncryptingException extends SecretVaultException {
    public EncryptingException() {
        super();
    }

    public EncryptingException(String message) {
        super(message);
    }

    public EncryptingException(String message, Throwable cause) {
    	super(message, cause);
    }

    public EncryptingException(Throwable cause) {
        super(cause);
    }

    protected EncryptingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
