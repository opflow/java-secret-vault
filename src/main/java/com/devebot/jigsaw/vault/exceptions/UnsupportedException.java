package com.devebot.jigsaw.vault.exceptions;

public class UnsupportedException extends AnsibleVaultException {
    public UnsupportedException() {
        super();
    }

    public UnsupportedException(String message) {
        super(message);
    }

    public UnsupportedException(String message, Throwable cause) {
    	super(message, cause);
    }

    public UnsupportedException(Throwable cause) {
        super(cause);
    }

    protected UnsupportedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
