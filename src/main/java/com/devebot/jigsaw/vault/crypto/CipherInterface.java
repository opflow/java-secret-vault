package com.devebot.jigsaw.vault.crypto;

import com.devebot.jigsaw.vault.core.VaultPayload;

public interface CipherInterface {
    public static final String ALGO_AES256 = "AES256";
    
    public VaultPayload encrypt(byte[] data, String password);
    public byte[] decrypt(VaultPayload payload, String password);
}
