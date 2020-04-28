package com.devebot.jigsaw.vault.exceptions;

public class AnsibleVaultException extends RuntimeException {
    public AnsibleVaultException() {
        super();
    }

    public AnsibleVaultException(String message) {
        super(message);
    }

    public AnsibleVaultException(String message, Throwable cause) {
    	super(message, cause);
    }

    public AnsibleVaultException(Throwable cause) {
        super(cause);
    }

    protected AnsibleVaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
