package com.devebot.jigsaw.vault.crypto.impl;

import com.devebot.jigsaw.vault.exceptions.KeyGenerationException;
import com.devebot.jigsaw.vault.utils.Randomizer;
import de.rtner.security.auth.spi.PBKDF2Engine;
import de.rtner.security.auth.spi.PBKDF2Parameters;
import java.util.Arrays;

public class KeyGenerator {
    private static final String CHAR_ENCODING = "UTF-8";
    private static final Randomizer randomizer = new Randomizer();
    
    private final String algo;
    private final String password;
    private final byte[] salt;
    private final int keyLen;
    private final int ivLen;
    private final int iterations;

    private byte [] hmacKey;
    private byte [] cipherKey;
    private byte [] iv;

    public KeyGenerator(String algo, String password, byte[] salt, int keyLen, int ivLen, int iterations) {
        this.algo = algo;
        this.password = password;
        this.salt = salt;
        this.keyLen = keyLen;
        this.ivLen = ivLen;
        this.iterations=iterations;
    }

    public KeyGenerator(String algo, String password, int saltLen, int keyLen, int ivLen, int iterations) {
        this(algo, password, generateSalt(saltLen), keyLen, ivLen, iterations);
    }

    public void generateKeys() {
        byte[] rawkeys = deriveKeys();
        this.cipherKey = Arrays.copyOfRange(rawkeys, 0, keyLen);
        this.hmacKey = Arrays.copyOfRange(rawkeys, keyLen, keyLen * 2);
        this.iv = Arrays.copyOfRange(rawkeys, keyLen * 2, keyLen * 2 + ivLen);
    }
    
    public byte[] getSalt() {
        return salt;
    }

    public byte[] getHmacKey() {
        return hmacKey;
    }
    
    public byte[] getCipherKey() {
        return cipherKey;
    }

    public byte[] getIv() {
        return iv;
    }
    
    private byte[] deriveKeys() {
        try {
            PBKDF2Engine pbkdf2Engine = new PBKDF2Engine(new PBKDF2Parameters(algo, CHAR_ENCODING, salt, iterations));
            return pbkdf2Engine.deriveKey(password, 2 * keyLen + ivLen);
        } catch (Exception ex) {
            throw new KeyGenerationException(ex);
        }
    }

    private static byte[] generateSalt(int length) {
        return randomizer.nextBytes(new byte[length]);
    }
}
