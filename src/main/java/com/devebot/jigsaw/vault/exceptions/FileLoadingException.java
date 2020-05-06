package com.devebot.jigsaw.vault.exceptions;

public class FileLoadingException extends SecretVaultException {
    public FileLoadingException() {
        super();
    }

    public FileLoadingException(String message) {
        super(message);
    }

    public FileLoadingException(String message, Throwable cause) {
    	super(message, cause);
    }

    public FileLoadingException(Throwable cause) {
        super(cause);
    }

    protected FileLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
