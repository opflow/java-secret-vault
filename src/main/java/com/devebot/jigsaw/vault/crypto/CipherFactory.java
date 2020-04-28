package com.devebot.jigsaw.vault.crypto;

import com.devebot.jigsaw.vault.crypto.impl.CipherImplAES256;
import com.devebot.jigsaw.vault.exceptions.UnsupportedException;

public class CipherFactory {
    private static CipherInterface cipherAES256 = null;
    
    public static CipherInterface getCipher(String algo) {
        if (algo == null) {
            throw new IllegalArgumentException("Algorithm must not be null");
        }
        switch (algo) {
            case CipherInterface.ALGO_AES256:
                if (cipherAES256 == null) {
                    synchronized (CipherImplAES256.class) {
                        if (cipherAES256 == null) {
                            cipherAES256 = new CipherImplAES256();
                        }
                    }
                }
                return cipherAES256;
            default:
                throw new UnsupportedException("Unsupported Algorithm");
        }
    }
}
