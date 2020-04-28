package com.devebot.jigsaw.vault.exceptions;

public class KeyGenerationException extends AnsibleVaultException {
    public KeyGenerationException() {
        super();
    }

    public KeyGenerationException(String message) {
        super(message);
    }

    public KeyGenerationException(String message, Throwable cause) {
    	super(message, cause);
    }

    public KeyGenerationException(Throwable cause) {
        super(cause);
    }

    protected KeyGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
