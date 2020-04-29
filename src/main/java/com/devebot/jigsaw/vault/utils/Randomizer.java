package com.devebot.jigsaw.vault.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class Randomizer {
    private Random r = null;
    
    public Randomizer() {
        getRandom();
    }
    
    public byte[] nextBytes(byte[] buffer) {
        if (buffer != null) {
            getRandom().nextBytes(buffer);
        }
        return buffer;
    }
    
    private Random getRandom() {
        if (r == null) {
            try {
                r = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                r = new Random();
            }
        }
        return r;
    }
}
