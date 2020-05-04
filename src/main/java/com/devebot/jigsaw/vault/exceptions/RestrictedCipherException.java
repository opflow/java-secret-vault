package com.devebot.jigsaw.vault.exceptions;

public class RestrictedCipherException extends SecretVaultException {
    public RestrictedCipherException() {
        super();
    }

    public RestrictedCipherException(String message) {
        super(message);
    }

    public RestrictedCipherException(String message, Throwable cause) {
    	super(message, cause);
    }

    public RestrictedCipherException(Throwable cause) {
        super(cause);
    }

    protected RestrictedCipherException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
