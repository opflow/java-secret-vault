package com.devebot.jigsaw.vault.exceptions;

public class DecryptingException extends AnsibleVaultException {
    public DecryptingException() {
        super();
    }

    public DecryptingException(String message) {
        super(message);
    }

    public DecryptingException(String message, Throwable cause) {
    	super(message, cause);
    }

    public DecryptingException(Throwable cause) {
        super(cause);
    }

    protected DecryptingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
