package com.devebot.jigsaw.vault.exceptions;

public class PaddingException extends SecretVaultException {
    public PaddingException() {
        super();
    }

    public PaddingException(String message) {
        super(message);
    }

    public PaddingException(String message, Throwable cause) {
    	super(message, cause);
    }

    public PaddingException(Throwable cause) {
        super(cause);
    }

    protected PaddingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
